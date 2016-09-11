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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.github.shareme.gwsfluidx.nonbinding.adaptable.AdaptableViewModel;

/**
 * A class that can bind a View Model to a ViewHolder.
 * Created by fgrott on 8/21/2016.
 */
@SuppressWarnings("unused")
public interface Binder<VM, VH extends RecyclerView.ViewHolder,
        AVM extends AdaptableViewModel<VM>> {

  /**
   * If the {@link AVM#getViewModel() viewModel} within the
   * {@code adaptableViewModel} is {@code null}, it has already been given to
   * { #onAdaptNeeded(AdaptableViewModel, int, Presenter)} to be queued
   * for adapting.
   */
  void onBindViewHolder(@NonNull AVM adaptableViewModel,
                        @NonNull VH viewHolder,
                        int position);
}
