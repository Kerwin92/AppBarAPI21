package com.brainstorm.neckup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.brainstorm.neckup.R;
import com.brainstorm.neckup.activity.ExerciseActivity;


/**
 * Created by kerwin on 15-5-7.
 */
public class CervicalFragment extends Fragment implements View.OnClickListener{
    private View rootView;
    private LinearLayout lltAm ,lltPm,lltNext;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedBundleInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cervical, container, false);
        /*设置此时toolbar的显示标题*/
        lltAm=(LinearLayout)rootView.findViewById(R.id.llt_cervical_am);
        lltAm.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llt_cervical_am:
                Intent intent=new Intent();
                intent.setClass(getActivity(), ExerciseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
