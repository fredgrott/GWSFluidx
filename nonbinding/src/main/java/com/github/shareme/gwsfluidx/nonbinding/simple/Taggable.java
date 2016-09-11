/*
  Copyright (C) 2016 Robert LaThanh
  Modifications Copyright(C) 2016 Fred Grott (GrottWorkShop)

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

package com.github.shareme.gwsfluidx.nonbinding.simple;

import android.support.annotation.Nullable;

/**
 * In order to notify the ViewHolder that the View Model is ready, a handle
 * to the ViewHolder is kept. In order to know that the View/ViewHolder
 * hasn't been recycled (bound to another item), a back-reference (tag) is
 * needed in the ViewHolder.
 * Created by fgrott on 9/9/2016.
 */
@SuppressWarnings("unused")
public interface Taggable {

  void setTag(@Nullable Object tag);
  @Nullable
  Object getTag();

}
