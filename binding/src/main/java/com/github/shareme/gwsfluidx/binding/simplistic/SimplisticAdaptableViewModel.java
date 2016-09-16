package com.github.shareme.gwsfluidx.binding.simplistic;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by fgrott on 9/16/2016.
 */

public class SimplisticAdaptableViewModel<A, VM> extends BaseObservable {
  @NonNull A adaptable;
  @Nullable VM viewModel;

  SimplisticAdaptableViewModel(@NonNull A adaptable) {
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