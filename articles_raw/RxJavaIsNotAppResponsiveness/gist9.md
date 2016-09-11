Original code developed by Robert LaThanh
Modifications by Fred Grott, all under Apache License 2.0 Copyright (C) 2016

```java
public interface AdaptableAdapter<VM, AVM extends AdaptableViewModel<VM>> {

  /**
   * Adapt the data contained in the {@code adaptable} into a ViewModel
   * ({@link VM}) viewModel that is ready to be bound to the View (displayed).
   *
   * The AdaptableAdapter implementation will save the ViewModel back into the
   * Adapable.
   *
   * When, precisely, this will happen depends on the {@link AdaptableBindingAdapter}
   * used.
   * One may decide to do all of the adapting before showing any items, while
   * another may initiate adapting for each item when it comes into view
   * (showing a loading indicator placeholder while adpating is occurring).
   *
   * @return the adapted version of this object, ready to be bound
   */
  @NonNull VM adapt(@NonNull AVM adaptable);

}


```

url:https://gist.github.com/shareme/3b78db5924d3612b30907d88acf2e864
