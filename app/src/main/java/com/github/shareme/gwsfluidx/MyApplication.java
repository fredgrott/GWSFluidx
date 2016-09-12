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
package com.github.shareme.gwsfluidx;

import android.app.Application;
import android.util.Log;

import timber.log.Timber;

/**
 * Just a basic extended application class
 * Created by fgrott on 8/28/2016.
 */
@SuppressWarnings("unused")
public class MyApplication extends Application {

  @Override
  public void onCreate(){
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree(){
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
          super.log(priority, "AppDemo" + tag, message, t);
        }

      });
    } else {
      Timber.plant(new CrashReportingTree());
    }

  }

  /** A tree which logs important information for crash reporting. */
  private static class CrashReportingTree extends Timber.Tree {
    @Override protected void log(int priority, String tag, String message, Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return;
      }

      FakeCrashLibrary.log(priority, tag, message);

      if (t != null) {
        if (priority == Log.ERROR) {
          FakeCrashLibrary.logError(t);
        } else if (priority == Log.WARN) {
          FakeCrashLibrary.logWarning(t);
        }
      }
    }
  }

}
