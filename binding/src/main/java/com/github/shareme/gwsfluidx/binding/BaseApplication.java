/*
 Copyright (C) 2016 Fred Grott(aka shareme GrottWorkShop)

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
either express or implied. See the License for the specific language
governing permissions and limitations under License.
 */
package com.github.shareme.gwsfluidx.binding;

import android.app.Application;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.github.shareme.gwsfluidx.binding.utils.ANRError;
import com.github.shareme.gwsfluidx.binding.utils.ANRWatchDog;
import com.github.shareme.gwsfluidx.binding.utils.DroidUuidFactory;
import com.github.shareme.gwsfluidx.binding.utils.MyCrashLibrary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Base Application class with the boilerplate that will
 * make you android app dev life easier.
 *
 * Usage:
 *
 * Either extend the class or copy the class and change what
 * you want to change to use.
 *
 * Features:
 *
 * 1. Root check and exit app if rooted as part of the securing
 *    user private data out of illegal hacker hands.
 *
 * 2. Log Wrapper And Analytics Set-Up
 *
 * 3. Top-Level Exception Set-up
 *
 *
 *
 * 4. ANR Watcher set-up so that we can more effectively detect ANRs
 *    beyond what StrictMode provides for
 *
 * 5. Strict Mode Wrapper set-up for targeting api 16 to api 24 apps
 *
 * Things Left Out:
 *
 * 1. Memory Leak detection using Jake Wharton's LeakCanary
 *
 * 2. The specialized settings in ANR WatchDog and the Log Wrapper set-up
 *    for the analytics library you are using, hence those methods
 *    are override-able.
 *
 * 3. The work-around for accessing a static context in a non-static way.
 *    The answer is on StackOverflow, Google it.
 *
 *
 *
 * Created by fgrott on 9/11/2016.
 */
@SuppressWarnings("unused")
public abstract class BaseApplication extends Application {

  public DroidUuidFactory myUUId;

  /**
   * you set this appTag to a string so that all through the
   * app any time you use a Timber log wrapper call it will
   * have the appTag label prefix.
   */
  public String appTag;

  /**
   * The domain string for invokeLogActivity feature of topExceptionSetup,
   * you need to set this if you use that feature of the topExceptionSetup.
   * It must match android:name in activity meta and intentiflter meta in
   * your Android Manifest.
   */
  public String invokeLogActivityDomain = "com.mydomain.SendLog";

  @Override
  public void onCreate() {
    super.onCreate();

    setUpUUID();

    topLevelExceptionSetUp();

    checkIfRoot();

    timberLogWrapperSetUp();

    anrWatchDogSetUp();
  }


  public void timberLogWrapperSetUp(){

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree(){
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
          super.log(priority, appTag + tag, message, t);
        }

      });
    } else {
      Timber.plant(new CrashReportingTree());
    }

  }


  /** A tree which logs important information for crash reporting.
   *  For your specific Analytics Crash library extend
   *  MyCrashLibrary, override this and replace
   *  MyCrashLibrary refs with the extended class
   *
   */
  private static class CrashReportingTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return;
      }

      MyCrashLibrary.log(priority, tag, message);

      if (t != null) {
        if (priority == Log.ERROR) {
          MyCrashLibrary.logError(t);
        } else if (priority == Log.WARN) {
          MyCrashLibrary.logWarning(t);
        }
      }
    }
  }



  public void topLevelExceptionSetUp() {
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, Throwable e) {
        handleUncaughtException(thread, e);
      }
    });


  }

  public boolean isUIThread(){
    return Looper.getMainLooper().getThread() == Thread.currentThread();
  }

  /**
   * If you are using a 3rd party analytics and logging than you might
   * want to put those hooks to sending log file in this method, ie
   * override this method
   */
  @SuppressWarnings("StatementWithEmptyBody")
  private void handleUncaughtException (Thread thread, Throwable e){
    e.printStackTrace(); // not all Android versions will print the stack trace automatically

    if(isUIThread()) {
      //invokeLogActivity();
    }else{  //handle non UI thread throw uncaught exception

      //new Handler(Looper.getMainLooper()).post(new Runnable() {
      // @Override
      // public void run() {
      // invokeLogActivity();
      //}
      //});
    }
  }


  /**
   * and in the manifest, if you are using the invokeLogActivity option:

   <code>
   <manifest xmlns:android="http://schemas.android.com/apk/res/android" ... >
   <!-- needed for Android 4.0.x and earlier -->
   <uses-permission android:name="android.permission.READ_LOGS" />

   <application ... >
   <activity
   android:name="com.mydomain.SendLog"
   android:theme="@android:style/Theme.Dialog"
   android:textAppearance="@android:style/TextAppearance.Large"
   android:windowSoftInputMode="stateHidden">
   <intent-filter>
   <action android:name="com.mydomain.SEND_LOG" />
   <category android:name="android.intent.category.DEFAULT" />
   </intent-filter>
   </activity>
   </application>
   </manifest>
   </code>

   */
  private void invokeLogActivity(){
    Intent intent = new Intent ();
    intent.setAction (invokeLogActivityDomain);
    intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity (intent);

    System.exit(1); // kill off the crashed app
  }





  /**
   * Sets up a User Unique ID that is encrypted for such uses
   * as analytics tracking, etc. You should never have to
   * override this.
   */
  public void setUpUUID(){
    myUUId =new DroidUuidFactory(this.getApplicationContext());

  }

  /**
   * Checks if root, if you need to change the behavior from system exiting
   * if root than override this provide your own implementation.
   */
  public void checkIfRoot(){
    // if device is rooted exit the program
    // to prevent some leakage of your user private data
    if(isRoot()){
      System.exit(1);
    }
  }

  /**
   * Sets up ANRWatchDog, you should not have to override
   * this with your implementation.
   */
  public void anrWatchDogSetUp(){
    if(!BuildConfig.DEBUG){
      new ANRWatchDog().setANRListener(new ANRWatchDog.ANRListener() {
        public void onAppNotResponding(ANRError error) {
          // Handle the error. For example, log it to HockeyApp: ExceptionHandler.saveException(error, new CrashManager());
        }
      }).start();

    }
  }

  /**
   * Checks to see if the normal root file system paths
   * exist and returns true if so. IE in other words if this
   * app is launched by a user who escalated their
   * privileges to super user than the app exits via the
   *
   * <code>
   *   if(isRoot()){
   *      System.exit(0);
   *   }
   * </code>
   *
   * call in the onCreate method of the Application class.
   *
   *
   * @return true if isRoot
   */
  public static boolean isRoot() {
    List<String> restrictedPaths = getRootFileSystemPaths();
    for (String filePath : restrictedPaths) {
      if (new File(filePath).exists()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Set up array to store paths to check to see if root
   * @return the paths array
   */
  private static List<String> getRootFileSystemPaths() {
    List<String> paths = new ArrayList<>(8);
    paths.add("/sbin/su");
    paths.add("/system/bin/su");
    paths.add("/system/xbin/su");
    paths.add("/data/local/bin/su");
    paths.add("/system/sd/xbin/su");
    paths.add("/system/bin/failsafe/su");
    paths.add("/data/local/su");
    return paths;
  }
}
