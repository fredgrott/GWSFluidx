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
package com.github.shareme.gwsfluidx.nonbinding;

import android.os.Parcelable;

/**
 * Subclass this for all your ViewModels
 * Created by fgrott on 8/21/2016.
 */
@SuppressWarnings("unused")
public abstract class ViewModel implements Parcelable{


  /**
   * When using instantrun we cannot use serialize
   * thus we need to set a unique id for RecyclerView's
   * stableIDs feature.
   *
   * use
   *
   * <code>
   *   id = System.currentTimeMillis();
   * </code>
   *
   * as it will be unique and not need any formatting.
   * Still need to set this up properly to be of equal use
   * as mutable and immutable.
   *
   * For immutables one needs two constructors with both returning the
   * static factory of the immutable. Constructor One should set the
   * Id with current time in millis as that will me a unique number.
   * Second constructor takes a parameter and sets that to the id before
   * returning the static immutable factory.
   *
   */
  abstract long id();




}
