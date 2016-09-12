/*
  Copyright 2016 Robert LaThanh
  Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)

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
package com.github.shareme.gwsfluidx.simple;

import com.android.annotations.NonNull;
import com.github.shareme.gwsfluidx.BaseFragment;
import com.github.shareme.gwsfluidx.BaseModels;
import com.github.shareme.gwsfluidx.binding.simple.SimpleAdapter;

/**
 * Adapts {@link BaseModels.DataModel}s into {@link BaseModels.ViewModel}s.
 *
 * <p>Not to be confused an Android Adapter, which does adapting and binding for
 * lists.</p>
 *
 * Created by fgrott on 8/24/2016.
 */

public class SimpleDemo_Adapter
        implements SimpleAdapter<BaseModels.ViewModel, BaseModels.DataModel> {

  @android.support.annotation.NonNull
  @NonNull
  @Override
  public BaseModels.ViewModel adapt(
          @android.support.annotation.NonNull @NonNull BaseModels.DataModel dataModel) {
    return BaseFragment.adaptDataModelToViewModel(dataModel);
  }
}
