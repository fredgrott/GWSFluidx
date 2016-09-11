Original code developed by Robert LaThanh
Modifications by Fred Grott, all under Apache License 2.0 Copyright (C) 2016


The AdaptableViewModel

```java
public class AdaptableViewModel<A, VM> extends BaseObservable {
  @NonNull A adaptable;
  @Nullable VM viewModel;

  AdaptableViewModel(@NonNull A adaptable) {
    this.adaptable = adaptable;
  }

  public void setViewModel(@NonNull VM viewModel) {
    this.viewModel = viewModel;
  }

  @Bindable
  @Nullable
  public VM getViewModel() {
    return viewModel;
  }
}


```

url:https://gist.github.com/shareme/9a823a7ba06a91a2b727b890df8deaa5
