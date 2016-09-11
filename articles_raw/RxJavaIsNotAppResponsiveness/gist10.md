Original code developed by Robert LaThanh
Modifications by Fred Grott, all under Apache License 2.0 Copyright (C) 2016

```java
public abstract class AdaptableBindingAdapter<VM,
        AVM extends AdaptableViewModel<VM>,
        VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

  /**
   * Provide the item at the position. This allows the binding adapter to queue
   * up the item for adapting if it hasn't already been adapted.
   */
  protected abstract @NonNull AVM get(int position);

  /**
   * This override is for documentation only.
   *
   * <p>Implementations must:
   * <ol>
   *   <li>Fetch the item to be bound,</li>
   *   <li>If the {@link AdaptableViewModel#getViewModel() ViewModel
   *        within the AdaptableViewModel} is {@code null}, the item
   *        must be queued for adapting (if it isn't already), and</li>
   *   <li>Pass the item to {@link #onBindViewHolder(RecyclerView.ViewHolder, AdaptableViewModel, int)}</li>
   * </ol>
   * </p>
   *
   * @param holder {@inheritDoc}
   * @param position {@inheritDoc}
   */
  @Override
  public abstract void onBindViewHolder(@NonNull VH holder, int position);

  /**
   * Perform binding of the {@code adaptableViewModel} to the
   * View / (Loading)ViewHolder.
   *
   * @param adaptableViewModel the item at {@code position}, for
   *        convenience since the implementation already retrieved it (and
   *        since a unique method signature is needed).
   *        The {@link AdaptableViewModel#getViewModel() ViewModel
   *        within the adaptableViewModel} may be {@code null}.
   *        If it is, the implementation has already queued up the item for
   *        adapting in the background.
   */
  public abstract void onBindViewHolder(@NonNull final VH viewHolder,
                                        @NonNull AVM adaptableViewModel,
                                        int position);

}

```

url:https://gist.github.com/shareme/c2d8e09552485e328161b9598b016863
