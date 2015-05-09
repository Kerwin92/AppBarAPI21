package com.example.appbar.activity;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbar.R;
import com.example.appbar.fragment.CervicalFragment;
import com.example.appbar.fragment.DrawerFragment;
import com.example.appbar.fragment.HealthFragment;
import com.example.appbar.fragment.RecordFragment;


public class MainActivity extends ActionBarActivity implements DrawerFragment.NavigationDrawerCallbacks {
    public static Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerFragment drawerFragment;
    private Fragment currentFragment, lastFragment;
    private ImageView ivNavIcon;
    private TextView tvNavTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivNavIcon = (ImageView) findViewById(R.id.iv_navIcon);
        tvNavTitle = (TextView) findViewById(R.id.tv_navTitle);
        //        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);

        // 打开 up bottom
        //        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setDrawerFragment();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // drawer toggle 并放入 toolbar
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        // Navigation Icon 要设定在 setSupoortActionBar 才有作用
        toolbar.setNavigationIcon(R.drawable.ic_launcher);
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
        /*设置打开的页面题头，启动后默认主页面的title写在此即可*/
        toolbar.setTitle("TestAppBar");
        /*设置默认显示的页面*/
        onNavigationDrawerItemSelected(0);
    }

    /**
     * 设置Toolbar的名称
     *
     * @param title
     */
    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
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
                    currentFragment = new CervicalFragment();
                    DrawerFragment.tvCervical.setBackgroundColor(getResources().getColor(R.color.drawer_bule));
                    DrawerFragment.tvRecord.setBackgroundColor(getResources().getColor(R.color.transparent));
                    DrawerFragment.tvHealth.setBackgroundColor(getResources().getColor(R.color.transparent));
                    ivNavIcon.setImageDrawable(getResources().getDrawable(R.drawable.giraffe));
                    tvNavTitle.setText(R.string.cervical);
                    break;
                case 1:
                    currentFragment = new RecordFragment();
                    DrawerFragment.tvRecord.setBackgroundColor(getResources().getColor(R.color.drawer_bule));
                    DrawerFragment.tvCervical.setBackgroundColor(getResources().getColor(R.color.transparent));
                    DrawerFragment.tvHealth.setBackgroundColor(getResources().getColor(R.color.transparent));
                    ivNavIcon.setImageDrawable(getResources().getDrawable(R.drawable.record));
                    tvNavTitle.setText(R.string.record);
                    break;
                case 2:
                    currentFragment = new HealthFragment();
                    DrawerFragment.tvHealth.setBackgroundColor(getResources().getColor(R.color.drawer_bule));
                    DrawerFragment.tvRecord.setBackgroundColor(getResources().getColor(R.color.transparent));
                    DrawerFragment.tvCervical.setBackgroundColor(getResources().getColor(R.color.transparent));
                    ivNavIcon.setImageDrawable(getResources().getDrawable(R.drawable.health));
                    tvNavTitle.setText(R.string.health);
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
