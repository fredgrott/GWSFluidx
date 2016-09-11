Original code developed by Robert LaThanh
Modifications by Fred Grott, all under Apache License 2.0 Copyright (C) 2016


SimpleBindingAdapter abstract class:

```java
public abstract class SimpleBindingAdapter<A, VM, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH>  {
  //== Operating fields =======================================================

  protected List<AdaptableViewModel<A, VM>> items;


  //== Abstract methods =======================================================

  /**
   * Perform binding of the {@code ViewModel} to the View / (Loading)ViewHolder.
   *
   * @param viewModel The ViewModel for the item at {@code position}.
   *        This may be {@code null}.
   *        If it is, the implementation has already queued up the item for
   *        adapting in the background.
   */
  public abstract void onBindViewHolder(@NonNull final VH viewHolder,
                                        @NonNull VM viewModel,
                                        int position);

  /**
   * When a View Model is given to a View (via
   * {@link #onBindViewHolder(RecyclerView.ViewHolder, Object, int)})
   * it may be {@code null} (adapting has not yet completed).
   * Once the adapting has been completed, the View/ViewHolder may need to be
   * notified (if it hasn't already been recycled) so that it can be updated to
   * show the ViewModel.
   *
   * This is called when the ViewModel is ready and the viewHolder has not been
   * recycled.
   * Implementations using Android Data Binding will simply just have to provide
   * the {@code viewModel} to the {@code viewHolder}'s binder.
   */
  protected abstract void onViewModelReadyForViewHolder(
          @NonNull VH viewHolder, @NonNull VM viewModel);


  //== 'RecyclerView.Adapter' methods =========================================

  @Override
  public int getItemCount() {
    return items.size();
  }


  //== 'SimpleBindingAdapter' methods =========================================

  public void addAll(Collection<A> adaptables) {
    if (items == null) {
      items = new ArrayList<>(adaptables.size());
    }

    for (A adaptable : adaptables) {
      items.add(new AdaptableViewModel<A, VM>(adaptable));
    }
  }

}


```

url: https://gist.github.com/shareme/90a14698604bc74d325aa4d977df964c
