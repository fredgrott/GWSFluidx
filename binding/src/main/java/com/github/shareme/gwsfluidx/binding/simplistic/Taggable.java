package com.github.shareme.gwsfluidx.binding.simplistic;

import android.support.annotation.Nullable;

/**
 * Created by fgrott on 9/16/2016.
 */
@SuppressWarnings("unused")
public interface Taggable {

  void setTag(@Nullable Object tag);
  @Nullable
  Object getTag();
}
