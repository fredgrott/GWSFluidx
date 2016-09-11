Original code developed by Robert LaThanh
Modifications by Fred Grott, all under Apache License 2.0 Copyright (C) 2016

```java
public abstract class AdaptOnDemandAdaptableBindingAdapter<VM,
        AVM extends AdaptableViewModel<VM>,
        VH extends RecyclerView.ViewHolder>
        extends AdaptableBindingAdapter<VM, AVM, VH>  {

  //== Operating fields =======================================================

  private final @NonNull
  AdaptableAdapter<VM, AVM> adaptableAdapter;
  private final @NonNull
  ExecutorService executorService;



  //== Constructors ===========================================================

  /**
   * Constructor for providing a custom executor service. For example, one that
   * uses fewer threads than the default, which means items would probably take
   * longer to adapt but may have less impact on the UI.
   */
  @SuppressWarnings("unused")
  public AdaptOnDemandAdaptableBindingAdapter(
          @NonNull AdaptableAdapter<VM, AVM> adaptableAdapter,
          @NonNull ExecutorService executorService) {
    this.adaptableAdapter = adaptableAdapter;
    this.executorService = executorService;
  }


  /**
   * The default executor service for adapting items uses a stack so the items
   * most recently scrolled into view are adapted first. Thus, items scrolled
   * into and then off of the screen before getting picked up for adapting are
   * adapted later. The number of threads it uses is up to double the number of
   * CPUs ({@code numCpus * 2 + 1}).
   */
  public AdaptOnDemandAdaptableBindingAdapter(
          @NonNull AdaptableAdapter<VM, AVM> adaptableAdapter) {
    this.adaptableAdapter = adaptableAdapter;

    // queue size must be at least as large as the number of items possibly on
    // the screen
    BlockingQueue<Runnable> queue = new LinkedBlockingDeque<Runnable>(128) {
      @Override
      public Runnable poll() {
        return super.pollLast();
      }
    };

    int cpus = Runtime.getRuntime().availableProcessors();
    this.executorService = new ThreadPoolExecutor(cpus + 1, cpus * 2 + 1,
            1, TimeUnit.SECONDS, queue);


  }

  //== 'RecyclerView.Adapter' methods =========================================

  @Override
  public void onBindViewHolder(@NonNull final VH loadingViewHolder,
                               int position) {
    final AVM adaptableViewModel = get(position);
    final VM adapted = adaptableViewModel.getViewModel();

    if (adapted == null) {
      // item not yet adapted. start task to make this view available
      new AsyncTask<Void, Void, VM>() {
        @Override
        protected VM doInBackground(Void... params) {
          if (adaptableViewModel.getViewModel() != null) {
            // another task must have beat me to adapting. (the item was
            // scrolled onto screen, I was created, the item was scrolled off
            // and back on, another duplicate task was added to the top of the
            // queue, that task finished, then I got executed.)
            return adaptableViewModel.getViewModel();
          }
          // this could still be a duplicate, concurrent job. oh well.
          return adaptableAdapter.adapt(adaptableViewModel);
        }

        @Override
        protected void onPostExecute(VM viewModel) {
          // save the adapted model to the adaptableViewModel so it's available
          // if it's requested again
          adaptableViewModel.setViewModel(viewModel);
        }
      }.executeOnExecutor(executorService);
    }

    // let implementation now do actual binding.
    onBindViewHolder(loadingViewHolder, adaptableViewModel, position);
  } // onBindViewHolder()


}


```
