/*
 * Copyright (c) 2015 Hannes Dorfmann.
 * Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.shareme.gwsfluidx.binding.delegates;

import android.support.annotation.NonNull;

/**
 * This class can be used as base class for a fallback delegate {@link
 * AdapterDelegatesManager#setFallbackDelegate(AdapterDelegate)}.
 *
 * Created by fgrott on 9/16/2016.
 */
@SuppressWarnings("unused")
public abstract class AbstractFallbackAdapterDelegate<T> implements AdapterDelegate<T> {

  /**
   * Not needed, because never called for fallback adapter delegates.
   *
   * @param items The data source of the Adapter
   * @param position The position in the datasource
   * @return true
   */
  @Override public boolean isForViewType(@NonNull Object items, int position) {
    return true;
  }
}
