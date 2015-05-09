package com.example.appbar.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appbar.R;
import com.example.appbar.activity.MainActivity;

public class DrawerFragment extends Fragment implements OnClickListener {
    private final String TAG = "DrawerFragment";
    private Context context;
    private View view;
    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationDrawerCallbacks mCallbacks;
    private int mCurrentSelectedPosition = 0;
    public static TextView tvCervical, tvRecord, tvHealth;
    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    "Activity must implement NavigationDrawerCallbacks.");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState
                    .getInt(STATE_SELECTED_POSITION);
        }
        Log.i(TAG, "mCurrentSelectedPosition: " + mCurrentSelectedPosition);
        //        selectItem(mCurrentSelectedPosition = 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        initView();
        context = getActivity();
        return view;
    }

    private void initView() {
        tvCervical = (TextView) view.findViewById(R.id.tv_cervical);
        tvRecord = (TextView) view.findViewById(R.id.tv_record);
        tvHealth = (TextView) view.findViewById(R.id.tv_health);
        tvCervical.setOnClickListener(this);
        tvRecord.setOnClickListener(this);
        tvHealth.setOnClickListener(this);
    }

    /**
     * Users of this fragment must call this method to set up the navigation
     * drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                MainActivity.toolbar, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().invalidateOptionsMenu();
            }
        };
        mDrawerToggle.syncState();
        //        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ab_android);
        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * Drawer中用户的选择
     *
     * @param position
     */
    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        //to do list……可添加实现点击后的状态变化效果
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
            /*监听到用户点击之后，在此处设置position，能调用到MainActivity中复写的此方法吗？？？*/
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cervical:
                selectItem(mCurrentSelectedPosition = 0);
                break;
            case R.id.tv_record:
                selectItem(mCurrentSelectedPosition = 1);
                break;
            case R.id.tv_health:
                selectItem(mCurrentSelectedPosition = 2);
                break;
            default:
                break;
        }
    }

    /**
     * Called when an item in the navigation drawer is selected.
     */
    public interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }
}