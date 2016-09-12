/*
  Copyright 2016 Robert LaThanh
  Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)

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
package com.github.shareme.gwsfluidx.adaptable;

import android.annotation.SuppressLint;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.github.shareme.gwsfluidx.BR;
import com.github.shareme.gwsfluidx.BaseModels;
import com.github.shareme.gwsfluidx.binding.adaptable.AbstractAdaptableViewModel;
import com.github.shareme.gwsfluidx.binding.adaptable.AdaptableViewModel;

/**
 * An {@link AdaptableViewModel} that has
 * a {@link BaseModels.DataModel} that can be adapted into a
 * {@link BaseModels.ViewModel}.
 *
 * Created by fgrott on 8/26/2016.
 */
@SuppressWarnings("unused")
public class AdaptableDemo_ViewModel extends AbstractAdaptableViewModel<BaseModels.ViewModel> {

  //-- Operating fields -------------------------------------------------------

  private long onBindTimeNanos;
  private @NonNull
  BaseModels.DataModel dataModel;


  //== Constructors ===========================================================

  public AdaptableDemo_ViewModel(@NonNull BaseModels.DataModel dataModel) {
    this.dataModel = dataModel;
  }

  //== Instance methods =======================================================

  @Override
  public void setViewModel(@NonNull BaseModels.ViewModel viewModel) {
    super.setViewModel(viewModel);
    notifyPropertyChanged(BR.viewModel);
    notifyPropertyChanged(BR.string);
  }


  //== 'AdaptableViewModel' methods ============================

  @NonNull
  public BaseModels.DataModel getDataModel() {
    return dataModel;
  }

  public void setOnBindTimeNanos(long onBindTimeNanos) {
    this.onBindTimeNanos = onBindTimeNanos;
  }

  @SuppressLint("DefaultLocale")
  @NonNull
  @Bindable
  public String getBindTime() {
    long elapsed = System.nanoTime() - onBindTimeNanos;
    return String.format("%.2f ms", elapsed / 1000000f);
  }

}
