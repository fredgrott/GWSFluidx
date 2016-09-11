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
package com.github.shareme.gwsfluidx.binding;

import com.github.shareme.gwsfluidx.binding.presenter.Presenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Borrowed from the EffectiveAndroid project:
 *
 *  https://github.com/rallat/EffectiveAndroid
 *
 *  Due to how the Presenter is de-coupled we cannot use Loaders in this case and
 *  thus this alternative solution. Allows us to manage the presenters during device
 *  orientation changes.
 *
 *  Usage:
 *
 *   in onSaveInstanceState
 *
 *   <code>
 *     PresenterHolder.getInstance().putPresenter(TopArticleListActivity.class, presenter);
 *
 *   </code>
 *
 *   in onDestroy
 *
 *   <code>
 *     if (this.isDetached()) {
 * PresenterHolder.getInstance().remove(TopArticleListActivity.class);
 * }
 *
 *   </code>
 *
 *   and in onCreate
 *
 *   <code>
 *     PresenterName presentervar = PresenterHolder.getInstance().getPresenter(TopArticleListPresenter.class);
 *
 *   </code>
 *
 *
 * Created by fgrott on 9/11/2016.
 */
@SuppressWarnings("unused")
public class PresenterHolder {

  static volatile PresenterHolder singleton = null;

  private Map<Class, Presenter> presenterMap;


  public static PresenterHolder getInstance() {
    if (singleton == null) {
      synchronized (PresenterHolder.class) {
        if (singleton == null) {
          singleton = new PresenterHolder();
        }
      }
    }
    return singleton;
  }

  private PresenterHolder() {
    this.presenterMap = new HashMap<>();
  }

  public void putPresenter(Class c, Presenter p) {
    presenterMap.put(c, p);
  }

  @SuppressWarnings("unchecked")
  public <T extends Presenter> T getPresenter(Class<T> c) {
    return (T) presenterMap.get(c);
  }

  public void remove(Class c) {
    presenterMap.remove(c);
  }

}
