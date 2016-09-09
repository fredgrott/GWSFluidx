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

package com.github.shareme.gwsfluidx.binding.simple;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Each Adaptable item in the list will be put into one of these containers
 * where it will be available for adapting (e.g., on demand, depending on the
 * implementation). This container will then also hold onto the adapted item,
 * the View Model.
 *
 * Created by fgrott on 9/9/2016.
 */
@SuppressWarnings("unused")
public class AdaptableViewModel<A, VM> extends BaseObservable {
  @NonNull A adaptable;
  @Nullable VM viewModel;

  AdaptableViewModel(@NonNull A adaptable) {
    this.adaptable = adaptable;
  }

  public void setViewModel(@NonNull VM viewModel) {
    this.viewModel = viewModel;
  }

  @Bindable
  @Nullable
  public VM getViewModel() {
    return viewModel;
  }
}

