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

import com.github.shareme.gwsfluidx.nonbinding.adaptable.AdaptableAdapter;
import com.github.shareme.gwsfluidx.nonbinding.adaptable.AdaptableViewModel;

/**
 * Contains everything needed to handle a view type:
 * <ul>
 *   <li>The {@link VHF ViewHolderFactory} that can create ViewHolders for the
 *       type</li>
 *   <li>The {@link AA AdaptableAdapter} that can adapt items for Binding
 *       (to the ViewHolder)</li>
 *   <li>The {@link Binder} that can bind the ViewModel to the ViewHolder</li>
 * </ul>
 *
 * Created by fgrott on 8/21/2016.
 */
@SuppressWarnings("unused")
public class Presenter<VM,
        AVM extends AdaptableViewModel<VM>,
        AA extends AdaptableAdapter<VM, AVM>,
        VH extends RecyclerView.ViewHolder,
        VHF extends ViewHolderFactory<VH>> {
  public final @NonNull VHF viewHolderFactory;
  public final @NonNull AA adaptableAdapter;
  public final @NonNull Binder<VM, VH, AVM> binder;

  public Presenter(@NonNull VHF viewHolderFactory,
                   @NonNull AA adaptableAdapter,
                   @NonNull Binder<VM, VH, AVM> binder) {
    this.viewHolderFactory = viewHolderFactory;
    this.adaptableAdapter = adaptableAdapter;
    this.binder = binder;
  }
} // class Presenter
