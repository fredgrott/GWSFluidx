Original code developed by Robert LaThanh
Modifications by Fred Grott, all under Apache License 2.0 Copyright (C) 2016

```java

public abstract class AbstractAdaptableViewModel<VM>
        extends BaseObservable
        implements AdaptableViewModel<VM> {

  private @Nullable VM viewModel;

  private long onBindTimeNanos;


  //== 'AdaptableViewModel' methods ===========================================

  @Bindable
  @Override
  public @Nullable
  VM getViewModel() {
    return viewModel;
  }

  @Override
  public void setViewModel(@NonNull VM viewModel) {
    this.viewModel = viewModel;
  }

  /**
   * set onBind time for debugging purposes
   * @param onBindTimeNanos the onBindTimeNanos var
   */
  public void setOnBindTimeNanos(long onBindTimeNanos) {
    this.onBindTimeNanos = onBindTimeNanos;
  }

  /**
   * get the bind time for debugging purposes
   * @return a string formatted time
   */
  @SuppressLint("DefaultLocale")
  @NonNull
  @Bindable
  public String getBindTime() {
    long elapsed = System.nanoTime() - onBindTimeNanos;
    return String.format("%.2f ms", elapsed / 1000000f);
  }


}


```

url:https://gist.github.com/shareme/89668576d7daa350adbac6f398e4b3dc
