package com.github.shareme.gwsfluidx.nonbinding;

import com.github.shareme.gwsfluidx.nonbinding.presenter.Presenter;

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
