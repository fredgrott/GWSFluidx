/*
  Copyright (C) 2016 Fred Grott(aka shareme GrottWorkShop)

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
either express or implied. See the License for the specific language
governing permissions and limitations under License.
 */
package com.github.shareme.gwsfluidx.nonbinding;

import android.app.Fragment;
import android.content.AsyncTaskLoader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Base Fragment class with the boilerplate
 * Created by fgrott on 9/1/2016.
 */
@SuppressWarnings("unused")
public abstract class BaseFragment<M> extends Fragment{

  protected RecyclerView recyclerView;

  protected BaseFragment(){

  }

  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    getLoadTask().execute();



  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_all, container, false);

    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    RecyclerView.LayoutManager layoutManager =
            new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);

    return view;
  }

  /**
   * Return an AsyncTask that will call {@link #loadData()} in its
   * {@link AsyncTask#doInBackground(Object[])}.
   *
   * Upon completion â€” in its {@link AsyncTask#onPostExecute(Object)} â€” it must
   * call the {@code onLoadedCallback}'s {@link Runnable#run()}, which will let
   * this base fragment know that data loading has completed so it can show the
   * list.
   */
  @NonNull
  protected abstract AsyncTask<Void, Void, List<M>>
  getLoadTask();

  protected abstract AsyncTaskLoader<List<M>> loadData();


}
