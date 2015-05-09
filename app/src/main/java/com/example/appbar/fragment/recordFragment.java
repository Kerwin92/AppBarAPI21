package com.example.appbar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbar.R;
import com.example.appbar.activity.MainActivity;

/**
 * Created by kerwin on 15-5-7.
 */
public class RecordFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedBundleInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        /*设置此时toolbar的显示标题*/
        ((MainActivity) getActivity()).setToolbarTitle(getResources().getString(R.string.record));
        return view;
    }

}
