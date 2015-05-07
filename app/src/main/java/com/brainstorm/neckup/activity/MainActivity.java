package com.brainstorm.neckup.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.brainstorm.neckup.fragment.DrawerFragment;
import com.brainstorm.neckup.fragment.Fragment0;
import com.brainstorm.neckup.fragment.Fragment1;
import com.brainstorm.neckup.fragment.Fragment2;

import biz.mosil.demo.navdrawer.R;


public class MainActivity extends ActionBarActivity implements DrawerFragment.NavigationDrawerCallbacks {
    public static Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerFragment drawerFragment;
    private Fragment currentFragment, lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setDrawerFragment();
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);

        // 打开 up bottom
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // drawer toggle 并放入 toolbar
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * 设置抽屉
     */
    private void setDrawerFragment() {
        drawerFragment = (DrawerFragment) getFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        drawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer));
        //        设置打开的页面题头为“攒人品”，启动后默认主页面的title写在此即可
        toolbar.setTitle("accumulate_character");
        onNavigationDrawerItemSelected(0);
    }

    /**
     * 根据导航抽屉的点击情况设置当前的显示页面
     *
     * @param position
     */
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        currentFragment = fragmentManager.findFragmentById(position);
        if (currentFragment == null) {
            switch (position) {
                case 0:
                    currentFragment = new Fragment0();
                    break;
                case 1:
                    currentFragment = new Fragment1();
                    break;
                case 2:
                    currentFragment = new Fragment2();
                    break;
                default:
                    break;
            }
            ft.add(R.id.container, currentFragment, getResources()
                    .getStringArray(R.array.nav_drawer_items)[position]);
        }
        if (lastFragment != null) {
            ft.hide(lastFragment);
        }
        if (currentFragment.isDetached()) {
            ft.attach(currentFragment);
        }
        ft.show(currentFragment);
        lastFragment = currentFragment;
        ft.commit();
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
