package com.github.shareme.gwsfluidx.presenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.github.shareme.gwsfluidx.binding.adaptable.AdaptableAdapter;
import com.github.shareme.gwsfluidx.binding.adaptable.AdaptableViewModel;
import com.github.shareme.gwsfluidx.binding.presenter.AdaptOnDemandPresenterBindingAdapter;
import com.github.shareme.gwsfluidx.binding.presenter.ItemViewType;
import com.github.shareme.gwsfluidx.binding.presenter.ViewHolderFactory;

import java.util.List;

/**
 * Created by fgrott on 9/12/2016.
 */

public class PresenterBindingAdapter<VM,
        AVM extends AdaptableViewModel<VM> & ItemViewType,
        AA extends AdaptableAdapter<VM, AVM>,
        VH extends RecyclerView.ViewHolder,
        VHF extends ViewHolderFactory<VH>>
        extends AdaptOnDemandPresenterBindingAdapter<VM, AVM, AA, VH, VHF> {

  private final List<AVM> items;

  public PresenterBindingAdapter(@NonNull LayoutInflater layoutInflater,
                        @NonNull List<AVM> items) {
    super(layoutInflater);
    this.items = items;
  }

  @Override
  protected @NonNull
  AVM get(int position) {
    return items.get(position);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

}
