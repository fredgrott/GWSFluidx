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
package com.github.shareme.gwsfluidx.simple;


import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.shareme.gwsfluidx.BaseFragment;
import com.github.shareme.gwsfluidx.BaseModels;
import com.github.shareme.gwsfluidx.binding.simple.AdaptOnDemandSimpleBindingAdapter;
import com.github.shareme.gwsfluidx.binding.simple.SimpleBindingAdapter;
import com.github.shareme.gwsfluidx.binding.simple.Taggable;
import com.github.shareme.gwsfluidx.databinding.SimpleListItemBinding;

import java.util.List;

/**
 * Demonstration of usage of {@link SimpleBindingAdapter}, the simple Adapter
 * Framework.
 * Created by fgrott on 8/24/2016.
 */

public class SimpleDemo_Fragment extends BaseFragment<BaseModels.DataModel> {

  //== Instantiation ==========================================================

  public static SimpleDemo_Fragment newInstance() {
    return new SimpleDemo_Fragment();
  }


  //== Instance methods =======================================================

  //-- 'BaseFragment' methods ------------------------------------

  /** A task to "load data", which simply returns data models as-is. */
  @NonNull
  @Override
  protected AsyncTask<Void, Void, List<BaseModels.DataModel>>
  getLoadTask(@NonNull final Runnable onLoadedCallback) {
    return new AsyncTask<Void, Void, List<BaseModels.DataModel>>() {
      @Override
      protected List<BaseModels.DataModel> doInBackground(Void... params) {
        return loadData();
      }

      @Override
      protected void onPostExecute(List<BaseModels.DataModel>
                                           dataModels) {
        BindingAdapter bindingAdapter = new BindingAdapter(getActivity(),
                dataModels);
        recyclerView.setAdapter(bindingAdapter);
        onLoadedCallback.run();
      }
    };
  }

  //== Inner classes ==========================================================

  public static class BindingAdapter
          extends AdaptOnDemandSimpleBindingAdapter<BaseModels.DataModel,
          BaseModels.ViewModel,
          ViewHolder> {

    private LayoutInflater inflater;

    public BindingAdapter(Context context,
                          List<BaseModels.DataModel> dataModels) {
      super(new SimpleDemo_Adapter());
      addAll(dataModels);
      this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      SimpleListItemBinding binding =
              SimpleListItemBinding.inflate(inflater, parent, false);
      return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder viewHolder,
            @NonNull BaseModels.ViewModel viewModel,
            int position) {
      viewHolder.setViewModel(viewModel);
    }

    @Override
    protected void onViewModelReadyForViewHolder(
            @NonNull ViewHolder viewHolder,
            @NonNull BaseModels.ViewModel viewModel) {
      viewHolder.setViewModel(viewModel);
    }
  } // class Adapter

  /**
   * The ViewHolder when using Data Binding is simple; it just uses the layout's
   * ViewDataBinding to do the binding.
   */
  public static class ViewHolder
          extends RecyclerView.ViewHolder
          implements Taggable {

    private final SimpleListItemBinding binding;
    private Object tag;

    public ViewHolder(SimpleListItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void setViewModel(BaseModels.ViewModel viewModel) {
      binding.setViewModel(viewModel);
    }

    @Override
    public void setTag(@Nullable Object tag) {
      this.tag = tag;
    }

    @Nullable
    @Override
    public Object getTag() {
      return this.tag;
    }
  } // class ViewHolder

}
