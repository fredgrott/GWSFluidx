Original code developed by Robert LaThanh
Modifications by Fred Grott, all under Apache License 2.0 Copyright (C) 2016


```java
@Override
  public void onBindViewHolder(final VH loadingViewHolder,
                               int position) {
    final AdaptableViewModel<A, VM> adaptableViewModel = items.get(position);
    VM viewModel = adaptableViewModel.viewModel;
    loadingViewHolder.setTag(adaptableViewModel);

    if (viewModel == null) {
      // item not yet adapted. start task to make this view available
      new AsyncTask<Void, Void, VM>() {
        @Override
        protected VM doInBackground(Void... params) {
          if (adaptableViewModel.viewModel != null) {
            // another task must have beat me to adapting. (the item was
            // scrolled onto screen, I was created, the item was scrolled off
            // and back on, another duplicate task was added to the top of the
            // queue, that task finished, then I got executed.)
            return adaptableViewModel.viewModel;
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
        }
      }.executeOnExecutor(executorService);
    }

    // let implementation now do actual binding.
    assert viewModel != null;
    onBindViewHolder(loadingViewHolder, viewModel, position);
  } // onBindViewHolder()


```

url:https://gist.github.com/shareme/7c698526acb2a296613d2371add30c1e
