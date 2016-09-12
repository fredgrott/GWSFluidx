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

/**
 * a fake crash library
 * Created by fgrott on 8/28/2016.
 */
@SuppressWarnings("unused")
public final class FakeCrashLibrary {

  public static void log(int priority, String tag, String message) {
    // TODO add log entry to circular buffer.
  }

  public static void logWarning(Throwable t) {
    // TODO report non-fatal warning.
  }

  public static void logError(Throwable t) {
    // TODO report non-fatal error.
  }

  private FakeCrashLibrary() {
    throw new AssertionError("No instances.");
  }

}
