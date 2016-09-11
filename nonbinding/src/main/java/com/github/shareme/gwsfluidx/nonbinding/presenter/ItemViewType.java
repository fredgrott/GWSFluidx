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
package com.github.shareme.gwsfluidx.nonbinding.presenter;

/**
 * In the PresenterBindingAdapter, all adapters are independent and unaware of
 * each other. So, in order for the PresenterBindingAdapter to be able to know
 * which adapter to use, the item must be able to identify its own type.
 *
 * It is recommended that this be the layout id of the layout for this item
 * type, as those are good unique identifiers that likely correlate well with
 * the appropriate adapter and binder.
 *
 * Created by fgrott on 8/21/2016.
 */
@SuppressWarnings("unused")
public interface ItemViewType {

  int getItemViewType();

}
