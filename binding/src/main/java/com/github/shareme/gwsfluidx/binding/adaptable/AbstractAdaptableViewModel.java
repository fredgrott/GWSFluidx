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
package com.github.shareme.gwsfluidx.binding.adaptable;

import android.annotation.SuppressLint;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A convenient {@link AdaptableViewModel} that stores the ViewModel once
 * it's ready (via {@link #setViewModel(Object)} and makes it available
 * (via {@link #getViewModel()}.
 * Other AdaptableViewModels that are already a subclass of something else
 * will need to do these themselves.
 *
 * Created by fgrott on 8/21/2016.
 */
@SuppressWarnings("unused")
public abstract class AbstractAdaptableViewModel<VM>
        extends BaseObservable
        implements AdaptableViewModel<VM> {

  private @Nullable VM viewModel;

  private long onBindTimeNanos;


  //== 'AdaptableViewModel' methods ===========================================

  @Bindable
  @Override
  public @Nullable
  VM getViewModel() {
    return viewModel;
  }

  @Override
  public void setViewModel(@NonNull VM viewModel) {
    this.viewModel = viewModel;
  }

  /**
   * set onBind time for debugging purposes
   * @param onBindTimeNanos the onBindTimeNanos var
   */
  public void setOnBindTimeNanos(long onBindTimeNanos) {
    this.onBindTimeNanos = onBindTimeNanos;
  }

  /**
   * get the bind time for debugging purposes
   * @return a string formatted time
   */
  @SuppressLint("DefaultLocale")
  @NonNull
  @Bindable
  public String getBindTime() {
    long elapsed = System.nanoTime() - onBindTimeNanos;
    return String.format("%.2f ms", elapsed / 1000000f);
  }


}
