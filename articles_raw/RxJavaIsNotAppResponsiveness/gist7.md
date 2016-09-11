Original code developed by Robert LaThanh
Modifications by Fred Grott, all under Apache License 2.0 Copyright (C) 2016

```java
public interface AdaptableViewModel<VM> {

  /**
   * Get the
   * already-{@link AdaptableAdapter#adapt(AdaptableViewModel) adapted}
   * version of this object, which had been saved by a previous call to
   * {@link #setViewModel(Object)}.
   *
   * @return the previously adapted object; {@code null} if it has not yet been
   *         adapted
   */
  @Nullable
  VM getViewModel();

  /**
   * Save the {@link AdaptableAdapter#adapt(AdaptableViewModel) adapted}
   * version of this object so that it's available when / next time it's needed.
   * If the item is still in view, this is the time to update the view; e.g.,
   * by notifying it that the view model has changed. This is the
   * responsibility of {@link AdaptableBindingAdapter} implementations.
   *
   * <p>Currently, there is no guarantee that the item will not be re-adapted
   * (for example, because of race conditions), so it is not safe to dereference
   * the adaptable data.</p>
   *
   * @param adapted the ViewModel for this item
   */
  void setViewModel(@NonNull VM adapted);

}

```

url:https://gist.github.com/shareme/ae950b68010fa0292e7d5ba572a2e0dd
