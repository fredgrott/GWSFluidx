package com.github.shareme.gwsfluidx.binding.simple;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A SimpleBindingAdapter that queues up unadapted items as they come into view.
 * So, when an item first comes into view, its View Model is {@code null} (and
 * a placeholder should be shown for the item).
 * Once adapting has completed, the ViewModel is given to the ViewHolder (if it
 * hasn't been recycled) to update the view.
 * The ViewModel is also saved for the next time the item is scrolled into view
 * again (so it can be shown immediately).
 *
 * @param <A> {@inheritDoc}
 * @param <VM> {@inheritDoc}
 * @param <VH> {@inheritDoc}
 *
 * Created by fgrott on 9/9/2016.
 */
@SuppressWarnings("unused")
public abstract class AdaptOnDemandSimpleBindingAdapter<A, VM,
        VH extends RecyclerView.ViewHolder &
                Taggable>
        extends SimpleBindingAdapter<A, VM, VH>  {

  private final @NonNull
  SimpleAdapter<VM, A> actualAdapter;


  private final @NonNull
  ExecutorService executorService;

  private  AsyncTask<Void, Void, VM> mTask = null;


  //== Constructors ===========================================================


  /**
   * Constructor for providing a custom executor service. For example, one that
   * uses fewer threads than the default, which means items would probably take
   * longer to adapt but may have less impact on the UI.
   */
  @SuppressWarnings("unused")
  public AdaptOnDemandSimpleBindingAdapter(
          @NonNull SimpleAdapter<VM, A> actualAdapter,
          @NonNull ExecutorService executorService) {
    this.actualAdapter = actualAdapter;
    this.executorService = executorService;
  }


  /**
   * The default executor service for adapting items uses a stack so the items
   * most recently scrolled into view are adapted first. Thus, items scrolled
   * into and then off of the screen before getting picked up for adapting are
   * adapted later. The number of threads it uses is up to double the number of
   * CPUs ({@code numCpus * 2 + 1}).
   */
  public AdaptOnDemandSimpleBindingAdapter(
          @NonNull SimpleAdapter<VM, A> actualAdapter) {
    this.actualAdapter = actualAdapter;

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

  /**
   * To fix to eliminate duplicate tasks, set an instance one to null
   * set the inner class to the instance and make sure to set
   * PostExecute and onCancelled to null again per this SO question and
   * answer:
   *
   * http://stackoverflow.com/questions/6645203/android%ADasynctask%ADavoid%ADmultiple%ADinstances%ADrunning5/5
   *
   *
   *
   * @param loadingViewHolder the loadingViewHolder
   * @param position the scroller position
   */
  @Override
  public void onBindViewHolder(final VH loadingViewHolder,
                               int position) {
    final AdaptableViewModel<A, VM> adaptableViewModel = items.get(position);
    VM viewModel = adaptableViewModel.getViewModel();
    loadingViewHolder.setTag(adaptableViewModel);

    if (viewModel == null) {
      if (mTask == null) {
        // item not yet adapted. start task to make this view available
       mTask = new AsyncTask<Void, Void, VM>() {
          @Override
          protected VM doInBackground(Void... params) {
            // Naming the AsyncTask via Xion's answer to this SO question:
            // http://stackoverflow.com/questions/7585426/android%ADidentify%ADwhat%ADcode%ADan%ADasynctask%ADis%ADrunning2/2
            Thread.currentThread().setName("Simple (AsyncTask)");
            if (adaptableViewModel.getViewModel() != null) {
              // another task must have beat me to adapting. (the item was
              // scrolled onto screen, I was created, the item was scrolled off
              // and back on, another duplicate task was added to the top of the
              // queue, that task finished, then I got executed.)

              return adaptableViewModel.getViewModel();
            }
            // this could still be a duplicate, concurrent job. oh well.

            return actualAdapter.adapt(adaptableViewModel.adaptable);
          }

          @Override
          protected void onPostExecute(VM viewModel) {
            // save the adapted model to the AdaptableViewModel so it's available
            // if it's requested again
            adaptableViewModel.setViewModel(viewModel);
            if (loadingViewHolder.getTag() == adaptableViewModel) {
              onViewModelReadyForViewHolder(loadingViewHolder, viewModel);
            }
            mTask = null;
          }

          @Override
          protected void onCancelled(){
            mTask = null;
          }

        };
        mTask.executeOnExecutor(executorService);
      }
    }

    // let implementation now do actual binding.
    assert viewModel != null;
    onBindViewHolder(loadingViewHolder, viewModel, position);
  } // onBindViewHolder()





}
