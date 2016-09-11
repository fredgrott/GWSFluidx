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
package com.github.shareme.gwsfluidx.binding.utils;

import android.os.Handler;
import android.os.Looper;

import timber.log.Timber;

/**
 * ANRWatchDog to use in your application class:
 *
 * <code>
 *
 *
 *     public void onCreate() {
 *           super.onCreate();
 *
 *          if (!BuildConfig.DEBUG) {
 *                 new ANRWatchDog().start();
 *         }
 *
 * }
 *
 * </code>
 *
 * or if not crashing the application upon anr than
 *
 * <code>
 *     public void onCreate(){
 *         super.onCreate():
 *
 *         if(!BuildConfig.DEBUG){
 *             new ANRWatchDog().setANRListener(new ANRWatchDog.ANRListener() {
 *
 *                   public void onAppNotResponding(ANRError error) {
 *                    // Handle the error. For example, log it to HockeyApp:
 *                    ExceptionHandler.saveException(error, new CrashManager());
 *                  }
 *            }).start();
 *
 *         }
 *     }
 * </code>
 *
 * Created by fgrott on 9/11/2016.
 */
@SuppressWarnings("unused")
public class ANRWatchDog extends Thread {

  public interface ANRListener {
    void onAppNotResponding(ANRError error);
  }

  public interface InterruptionListener {
    void onInterrupted(InterruptedException exception);
  }

  private static final int DEFAULT_ANR_TIMEOUT = 5000;

  private static final ANRListener DEFAULT_ANR_LISTENER = new ANRListener() {
    @Override public void onAppNotResponding(ANRError error) {
      throw error;
    }
  };

  private static final InterruptionListener DEFAULT_INTERRUPTION_LISTENER = new InterruptionListener() {
    @Override public void onInterrupted(InterruptedException exception) {
      Timber.w("ANRWatchdog", "Interrupted: " + exception.getMessage());
    }
  };

  private ANRListener _anrListener = DEFAULT_ANR_LISTENER;
  private InterruptionListener _interruptionListener = DEFAULT_INTERRUPTION_LISTENER;

  private final Handler _uiHandler = new Handler(Looper.getMainLooper());
  private final int _timeoutInterval;

  private String _namePrefix = "";
  private boolean _logThreadsWithoutStackTrace = false;

  private volatile int _tick = 0;

  private final Runnable _ticker = new Runnable() {
    @Override public void run() {
      _tick = (_tick + 1) % 10;
    }
  };

  /**
   * Constructs a watchdog that checks the ui thread every {@value #DEFAULT_ANR_TIMEOUT} milliseconds
   */
  public ANRWatchDog() {
    this(DEFAULT_ANR_TIMEOUT);
  }

  /**
   * Constructs a watchdog that checks the ui thread every given interval
   *
   * @param timeoutInterval The interval, in milliseconds, between to checks of the UI thread.
   *                        It is therefore the maximum time the UI may freeze before being reported as ANR.
   */
  public ANRWatchDog(int timeoutInterval) {
    super();
    _timeoutInterval = timeoutInterval;
  }

  /**
   * Sets an interface for when an ANR is detected.
   * If not set, the default behavior is to throw an error and crash the application.
   *
   * @param listener The new listener or null
   * @return itself for chaining.
   */
  public ANRWatchDog setANRListener(ANRListener listener) {
    if (listener == null) {
      _anrListener = DEFAULT_ANR_LISTENER;
    }
    else {
      _anrListener = listener;
    }
    return this;
  }

  /**
   * Sets an interface for when the watchdog thread is interrupted.
   * If not set, the default behavior is to just log the interruption message.
   *
   * @param listener The new listener or null.
   * @return itself for chaining.
   */
  public ANRWatchDog setInterruptionListener(InterruptionListener listener) {
    if (listener == null) {
      _interruptionListener = DEFAULT_INTERRUPTION_LISTENER;
    }
    else {
      _interruptionListener = listener;
    }
    return this;
  }

  /**
   * Set the prefix that a thread's name must have for the thread to be reported.
   * Note that the main thread is always reported.
   *
   * @param prefix The thread name's prefix for a thread to be reported.
   * @return itself for chaining.
   */
  public ANRWatchDog setReportThreadNamePrefix(String prefix) {
    if (prefix == null)
      prefix = "";
    _namePrefix = prefix;
    return this;
  }

  /**
   * Set that only the main thread will be reported.
   *
   * @return itself for chaining.
   */
  public ANRWatchDog setReportMainThreadOnly() {
    _namePrefix = null;
    return this;
  }

  public void setLogThreadsWithoutStackTrace(boolean logThreadsWithoutStackTrace) {
    _logThreadsWithoutStackTrace = logThreadsWithoutStackTrace;
  }

  @Override
  public void run() {
    setName("|ANR-WatchDog|");

    int lastTick;
    while (!isInterrupted()) {
      lastTick = _tick;
      _uiHandler.post(_ticker);
      try {
        Thread.sleep(_timeoutInterval);
      }
      catch (InterruptedException e) {
        _interruptionListener.onInterrupted(e);
        return ;
      }

      // If the main thread has not handled _ticker, it is blocked. ANR.
      if (_tick == lastTick) {
        ANRError error;
        if (_namePrefix != null)
          error = ANRError.New(_namePrefix, _logThreadsWithoutStackTrace);
        else
          error = ANRError.NewMainOnly();
        _anrListener.onAppNotResponding(error);
        return ;
      }
    }
  }


}
