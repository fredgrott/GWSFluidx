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
package com.github.shareme.gwsfluidx;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.shareme.gwsfluidx.adaptable.AdaptableDemo_Fragment;
import com.github.shareme.gwsfluidx.presenter.PresenterDemo_Fragment;
import com.github.shareme.gwsfluidx.simple.SimpleDemo_Fragment;

/**
 * Created by fgrott on 8/24/2016.
 */

public class MainActivity extends AppCompatActivity {

  /**
   * The {@link #onCreateOptionsMenu(Menu) Options Menu} options and where they should go.
   */
  public enum MenuOption {
    SIMPLE(R.id.simple_demo) {
      @Override
      public Fragment newInstance() {
        return SimpleDemo_Fragment.newInstance();
      }
    },
    ADAPTABLE(R.id.adaptable_demo) {
      @Override
      public Fragment newInstance() {
        return AdaptableDemo_Fragment.newInstance();
      }
    },
    FULL(R.id.full_demo) {
      @Override
      public Fragment newInstance() {
        return new PresenterDemo_Fragment();
      }
    },;

    final int menuId;

    MenuOption(int menuId) {
      this.menuId = menuId;
    }

    public abstract Fragment newInstance();
  }


  //-- 'Activity' methods -----------------------------------------------------

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.app_name);
    setSupportActionBar(toolbar);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    FragmentManager fragmentManager = getFragmentManager();

    if (id == R.id.clear) {
      Fragment activeFragment =
              fragmentManager.findFragmentById(R.id.container);
      if (activeFragment != null) {
        fragmentManager.beginTransaction()
                .remove(activeFragment)
                .setBreadCrumbTitle(R.string.clear)
                .commit();
      }
      return true;
    } else {
      for (MenuOption menuOption : MenuOption.values()) {
        if (id == menuOption.menuId) {
          fragmentManager.beginTransaction()
                  .replace(R.id.container, menuOption.newInstance())
                  .setBreadCrumbTitle(item.getTitle())
                  .commit();
          return true;
        }
      }
    }

    return super.onOptionsItemSelected(item);
  } // onOptionsItemSelected()
}