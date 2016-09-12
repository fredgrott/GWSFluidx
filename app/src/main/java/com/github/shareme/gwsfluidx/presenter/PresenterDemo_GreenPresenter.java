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
import com.github.shareme.gwsfluidx.databinding.PresenterGreenListItemBinding;

import java.text.DateFormat;
import java.util.Date;

/**
 * Green items show their timestamp as a Date (compared to Red items which show
 * the number of milliseconds that have elapsed since Unix Epoch).
 * Upon long-click, the item updates its timestamp, revert its visual state to
 * un-adapted while it does so (compared with Red items that "disable"
 * themselves).
 *
 * Created by fgrott on 8/26/2016.
 */
@SuppressWarnings("unused")
public class PresenterDemo_GreenPresenter implements ViewHolderFactory<PresenterDemo_GreenPresenter.GreenViewHolder>,
        AdaptableAdapter<PresenterDemo_GreenPresenter.GreenViewModel,
                        PresenterDemo_GreenPresenter.GreenAdaptableViewModel>,
        Binder<PresenterDemo_GreenPresenter.GreenViewModel,
                        PresenterDemo_GreenPresenter.GreenViewHolder,
                        PresenterDemo_GreenPresenter.GreenAdaptableViewModel> {

  //== Constants ==============================================================

  private static final int GREEN_LAYOUT_ID =
          R.layout.presenter_green_list_item;

  private static final DateFormat DATE_FORMAT =
          DateFormat.getDateTimeInstance();


  //== Inner classes ==========================================================

  public static class GreenAdaptableViewModel
          extends AbstractAdaptableViewModel<GreenViewModel>
          implements ItemViewType {
    final int ordinal;
    final int delay;
    Date time;

    public GreenAdaptableViewModel(int ordinal, int delay, Date time) {
      this.ordinal = ordinal;
      this.delay = delay;
      this.time = time;
    }

    @Override
    public void setViewModel(
            @android.support.annotation.NonNull @NonNull GreenViewModel viewModel) {
      super.setViewModel(viewModel);
      notifyPropertyChanged(BR.viewModel);
    }

    @Override
    public int getItemViewType() {
      return GREEN_LAYOUT_ID;
    }
  }

  public static class GreenViewModel extends BaseObservable {
    private final String title;
    private final String description;
    private boolean isSelected;

    public GreenViewModel(String title, String description) {
      this.title = title;
      this.description = description;
    }

    @Bindable
    public String getTitle() {
      return title;
    }

    @Bindable
    public String getDescription() {
      return description;
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

  public static class GreenViewHolder extends RecyclerView.ViewHolder {
    private final PresenterGreenListItemBinding binding;
    private final ViewListener viewListener;

    public static GreenViewHolder newInstance(LayoutInflater inflater,
                                              ViewGroup parent,
                                              ViewListener
                                                      viewListener) {
      PresenterGreenListItemBinding binding =
              PresenterGreenListItemBinding.inflate(inflater, parent, false);
      return new GreenViewHolder(binding, viewListener);
    }

    private GreenViewHolder(PresenterGreenListItemBinding binding,
                            ViewListener viewListener) {
      super(binding.getRoot());
      this.binding = binding;
      this.viewListener = viewListener;
      binding.checked.setOnCheckedChangeListener(viewListener);
      binding.content.setOnLongClickListener(viewListener);
    }

    public void setGreenAdaptableViewModel(GreenAdaptableViewModel viewModel) {
      binding.setAdaptableViewModel(viewModel);
      viewListener.setAdaptableViewModel(viewModel);
    }
  } // class GreenViewHolder

  private class ViewListener
          implements CompoundButton.OnCheckedChangeListener,
          View.OnLongClickListener {

    private GreenAdaptableViewModel adaptableViewModel;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
      GreenViewModel viewModel = this.adaptableViewModel.getViewModel();
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
              PresenterDemo_GreenPresenter.this.onItemLongClick(
                      adaptableViewModel);
    }

    public void setAdaptableViewModel(GreenAdaptableViewModel
                                              adaptableViewModel) {
      this.adaptableViewModel = adaptableViewModel;
    }
  } // class ViewListener


  //== Instance methods =======================================================

  public int getLayoutId() {
    return GREEN_LAYOUT_ID;
  }

  @android.support.annotation.NonNull
  @NonNull
  @Override
  public GreenViewModel adapt(@android.support.annotation.NonNull @NonNull GreenAdaptableViewModel
                                      greenAdaptableViewModel) {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // don't care
    }
    return new GreenViewModel(
            Integer.toString(greenAdaptableViewModel.ordinal),
            ((DateFormat)DATE_FORMAT.clone()).format(greenAdaptableViewModel.time));
  }

  @Override
  public GreenViewHolder onCreateViewHolder(@android.support.annotation.NonNull @NonNull LayoutInflater inflater,
                                            @Nullable ViewGroup parent,
                                            int viewType) {
    return GreenViewHolder.newInstance(inflater, parent, new ViewListener());
  }


  @Override
  public void onBindViewHolder(
          @android.support.annotation.NonNull @NonNull GreenAdaptableViewModel adaptableViewModel,
          @android.support.annotation.NonNull @NonNull GreenViewHolder viewHolder,
          int position) {
    viewHolder.setGreenAdaptableViewModel(adaptableViewModel);
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
  private boolean onItemLongClick(@NonNull final GreenAdaptableViewModel
                                          adaptableViewModel) {
    new AsyncTask<Void, Void, Void>() {
      @SuppressWarnings("ConstantConditions")
      @Override
      protected void onPreExecute() {
        adaptableViewModel.setViewModel(null);
      }

      @Override
      protected Void doInBackground(Void... params) {
        try {
          Thread.sleep(1234);
        } catch (InterruptedException e) {
          // don't care
        }

        adaptableViewModel.time = new Date();

        // event completed. now adapt a new ViewModel
        adaptableViewModel.setViewModel(adapt(adaptableViewModel));
        return null;
      }
    }.execute();
    return true;
  }

}
