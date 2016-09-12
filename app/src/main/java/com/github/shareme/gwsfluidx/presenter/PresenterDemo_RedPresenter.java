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
package com.github.shareme.gwsfluidx.presenter;

import android.annotation.SuppressLint;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.github.shareme.gwsfluidx.BR;
import com.github.shareme.gwsfluidx.R;
import com.github.shareme.gwsfluidx.binding.adaptable.AbstractAdaptableViewModel;
import com.github.shareme.gwsfluidx.binding.adaptable.AdaptableAdapter;
import com.github.shareme.gwsfluidx.binding.presenter.Binder;
import com.github.shareme.gwsfluidx.binding.presenter.ItemViewType;
import com.github.shareme.gwsfluidx.binding.presenter.ViewHolderFactory;
import com.github.shareme.gwsfluidx.databinding.PresenterRedListItemBinding;

/**
 * Red items show their timestamp as the number of milliseconds that have
 * elapsed since Unix Epoch (compared to Green items which use a DateFormat).
 * Upon long-click, the item updates its timestamp, "disables" itself while it
 * does so (compared with Green items that revert to their loading state).
 *
 * Created by fgrott on 8/26/2016.
 */
@SuppressWarnings("unused")
public class PresenterDemo_RedPresenter implements ViewHolderFactory<PresenterDemo_RedPresenter.RedViewHolder>,
        AdaptableAdapter<PresenterDemo_RedPresenter.RedViewModel,
                        PresenterDemo_RedPresenter.RedAdaptableViewModel>,
        Binder<PresenterDemo_RedPresenter.RedViewModel,
                        PresenterDemo_RedPresenter.RedViewHolder,
                        PresenterDemo_RedPresenter.RedAdaptableViewModel> {

  //== Constants ==============================================================

  private static final int RED_LAYOUT_ID =
          R.layout.presenter_red_list_item;


  //== Inner classes ==========================================================

  public static class RedAdaptableViewModel
          extends AbstractAdaptableViewModel<RedViewModel>
          implements ItemViewType {
    final int ordinal;
    final int delay;
    long currentTimeMillis;
    boolean processingInProgress;

    public RedAdaptableViewModel(int ordinal, int delay, long currentTimeMillis) {
      this.ordinal = ordinal;
      this.delay = delay;
      this.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public void setViewModel(
            @android.support.annotation.NonNull @NonNull RedViewModel viewModel) {
      super.setViewModel(viewModel);
      notifyPropertyChanged(BR.viewModel);
    }

    @Bindable
    public boolean isProcessingInProgress() {
      return processingInProgress;
    }

    public void setProcessingInProgress(boolean processingInProgress) {
      this.processingInProgress = processingInProgress;
      notifyPropertyChanged(BR.processingInProgress);
    }

    @Override
    public int getItemViewType() {
      return RED_LAYOUT_ID;
    }
  }

  public static class RedViewModel extends BaseObservable {
    private final String one;
    private final String two;
    private boolean isSelected;

    private RedViewModel(String one, String two) {
      this.one = one;
      this.two = two;
    }

    @Bindable
    public String getOne() {
      return one;
    }

    @Bindable
    public String getTwo() {
      return two;
    }

    @Bindable
    public boolean isSelected() {
      return isSelected;
    }

    public void setSelected(boolean selected) {
      isSelected = selected;
      notifyPropertyChanged(BR.selected);
    }
  }

  public static class RedViewHolder extends RecyclerView.ViewHolder {
    private PresenterRedListItemBinding binding;
    private final ViewListener viewListener;

    public static RedViewHolder newInstance(LayoutInflater inflater,
                                            ViewGroup parent,
                                            ViewListener
                                                    viewListener) {
      PresenterRedListItemBinding binding =
              PresenterRedListItemBinding.inflate(inflater, parent, false);
      return new RedViewHolder(binding, viewListener);
    }

    private RedViewHolder(PresenterRedListItemBinding binding,
                          ViewListener viewListener) {
      super(binding.getRoot());
      this.binding = binding;
      this.viewListener = viewListener;
      binding.checked.setOnCheckedChangeListener(viewListener);
      binding.content.setOnLongClickListener(viewListener);
    }

    public void setRedAdaptableViewModel(RedAdaptableViewModel viewModel) {
      binding.setAdaptableViewModel(viewModel);
      viewListener.setAdaptableViewModel(viewModel);
    }
  } // class RedViewHolder

  private class ViewListener
          implements CompoundButton.OnCheckedChangeListener,
          View.OnLongClickListener {

    private RedAdaptableViewModel adaptableViewModel;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
      RedViewModel viewModel = this.adaptableViewModel.getViewModel();
      if (viewModel == null) {
        // item is being scrolled into view and is not yet adapted, but the
        // checkbox is being set by Data Binding anyway
        return;
      }
      viewModel.setSelected(isChecked);
    }

    @Override
    public boolean onLongClick(View v) {
      return
              PresenterDemo_RedPresenter.this.onItemLongClick(
                      adaptableViewModel);
    }

    public void setAdaptableViewModel(RedAdaptableViewModel
                                              adaptableViewModel) {
      this.adaptableViewModel = adaptableViewModel;
    }
  } // class ViewListener


  //== Instance methods =======================================================

  public int getLayoutId() {
    return RED_LAYOUT_ID;
  }

  @SuppressLint("DefaultLocale")
  @android.support.annotation.NonNull
  @NonNull
  @Override
  public RedViewModel adapt(@android.support.annotation.NonNull @NonNull RedAdaptableViewModel
                                    redAdaptableViewModel) {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // don't care
    }
    return new RedViewModel(
            Integer.toString(redAdaptableViewModel.ordinal),
            String.format("%,d", redAdaptableViewModel.currentTimeMillis));
  }

  @Override
  public RedViewHolder onCreateViewHolder(@android.support.annotation.NonNull @NonNull LayoutInflater inflater,
                                          @Nullable ViewGroup parent,
                                          int viewType) {
    return RedViewHolder.newInstance(inflater, parent, new ViewListener());
  }


  @Override
  public void onBindViewHolder(
          @android.support.annotation.NonNull @NonNull RedAdaptableViewModel adaptableViewModel,
          @android.support.annotation.NonNull @NonNull RedViewHolder viewHolder,
          int position) {
    viewHolder.setRedAdaptableViewModel(adaptableViewModel);
  }

  /**
   * An item in the list has been long-clicked.
   * We'll emulate some long-running (a second or two) task that will modify the
   * data (Data Model), which will then update the view.
   *
   * <p>In this example, the adaptableViewModel is the source data itself.
   * In a more real case, it might contain an identifier that can be used to
   * perform a request, and when that request returns it modifies the underlying
   * data.</p>
   *
   * @return always {@code true}
   */
  private boolean onItemLongClick(@NonNull final RedAdaptableViewModel
                                          adaptableViewModel) {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected void onPreExecute() {
        adaptableViewModel.setProcessingInProgress(true);
      }

      @Override
      protected Void doInBackground(Void... params) {
        try {
          Thread.sleep(1234);
        } catch (InterruptedException e) {
          // don't care
        }

        adaptableViewModel.currentTimeMillis = System.currentTimeMillis();

        // event completed. now adapt a new ViewModel
        adaptableViewModel.setViewModel(adapt(adaptableViewModel));
        return null;
      }

      @Override
      protected void onPostExecute(Void voyd) {
        adaptableViewModel.setProcessingInProgress(false);
      }
    }.execute();
    return true;
  }

}
