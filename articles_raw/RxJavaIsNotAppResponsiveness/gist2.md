Original code developed by Robert LaThanh
Modifications by Fred Grott, all under Apache License 2.0 Copyright (C) 2016


The SimpleAdapter interface


```java
public interface SimpleAdapter<VM, A> {

  /**
   * Adapt the data contained in the {@code adaptable} into a ViewModel
   * ({@link VM}) viewModel that is ready to be bound to the View (displayed).
   *
   * When, precisely, this will happen depends on the
   * {@link SimpleBindingAdapter} used.
   * One may decide to do all of the adapting before showing any items, while
   * another may initiate adapting for each item when it comes into view
   * (showing a loading indicator placeholder while adpating is occurring).
   *
   * @return the adapted version of this object, ready to be bound
   */
  @NonNull VM adapt(@NonNull A adaptable);

}

```

url: https://gist.github.com/shareme/afb8c9e6bf1e246675c66d47a4edf214
