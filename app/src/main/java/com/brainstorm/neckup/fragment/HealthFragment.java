package com.brainstorm.neckup.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brainstorm.neckup.R;
import com.brainstorm.neckup.Utils.ScoreRoundProgressBar;
import com.brainstorm.neckup.activity.HealthAssessActivity;

import java.util.Random;

/**
 * Created by Administrator on 2015/5/15.
 */
public class HealthFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "NewAssessReportActivity";
    private Button btnReAssess;
    private View rootView;
    private ScoreRoundProgressBar mScoreRoundProgressBar;
    private int iterationSum[];
    private int score;
    private int userChoose[];
    private Intent intent;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedBundleInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_health_report, container, false);
        initView();

        mScoreRoundProgressBar.setProgress(50);
        return rootView;
    }

    /**
     * 初始化控件
     */
    public void initView() {
        btnReAssess = (Button) rootView.findViewById(R.id.btn_reassess);
        mScoreRoundProgressBar = (ScoreRoundProgressBar) rootView.findViewById(R.id.score_round_progressBar);
        btnReAssess.setOnClickListener(this);
    }

    /**
     * 监听实现
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reassess:
                Intent intent1 = new Intent();
                intent1.setClass( getActivity(), HealthAssessActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    /**
     * 计算用户得分
     */
    private void countScore() {
        score = -1;
        userChoose = new int[HealthAssessActivity.QUESTION_NUM];
        iterationSum = new int[HealthAssessActivity.QUESTION_NUM];
        for (int i = 0; i < HealthAssessActivity.QUESTION_NUM; i++)
            iterationSum[i] = 0;
        // userChoose 初始化
        intent = getActivity().getIntent();
        userChoose = intent.getIntArrayExtra("qArray");
        Log.i(TAG, "userChoose: " + userChoose);
        iterationSum[0] = userChoose[0];
        for (int i = 1; i < HealthAssessActivity.QUESTION_NUM; i++) {
            iterationSum[i] += (iterationSum[i - 1] + userChoose[i]);
            Log.i(TAG, "iterationSum[" + i + "]: " + iterationSum[i]);
        }
        /* 86-100健康状态 */
        // 86-89
        if (iterationSum[12] - iterationSum[4] == 0 && iterationSum[1] == 0
                && iterationSum[4] - iterationSum[1] == 1) {
            score = (Math.abs(new Random().nextInt())) % 4 + 86;
        }
        // 90-92
        if (iterationSum[12] - iterationSum[4] == 0 && iterationSum[1] == 1
                && iterationSum[4] - iterationSum[1] == 1) {
            score = (Math.abs(new Random().nextInt())) % 3 + 90;
        }
        // 93-94
        if (iterationSum[12] - iterationSum[4] == 0 && iterationSum[1] == 2
                && iterationSum[4] - iterationSum[1] == 1) {
            score = (Math.abs(new Random().nextInt())) % 2 + 93;
        }
        // 95-97
        if (iterationSum[12] - iterationSum[1] == 0 && iterationSum[1] == 1) {
            score = (Math.abs(new Random().nextInt())) % 3 + 95;
        }
        // 98-100
        if (iterationSum[12] - iterationSum[1] == 0 && iterationSum[1] == 2) {
            score = (Math.abs(new Random().nextInt())) % 3 + 98;
        }
		/* 49-85颈椎病预警 */
        // 75-85
        if (iterationSum[12] - iterationSum[4] == 0
                && iterationSum[4] - iterationSum[1] >= 2) {
            score = 85 - 2
                    * (iterationSum[2] + 2 - iterationSum[4] - iterationSum[2])
                    + 2 * (iterationSum[4] - iterationSum[2]);
        }
        // 60-74
        if (iterationSum[7] - iterationSum[4] == 1
                && iterationSum[12] - iterationSum[7] == 0) {
            score = 75 - 3
                    * (iterationSum[2] + 2 - iterationSum[4] - iterationSum[2])
                    + 2 * (iterationSum[4] - iterationSum[2]);
        }
		/* 0-59已患颈椎病 */
        // 49-59
        if (iterationSum[7] - iterationSum[4] >= 2
                && iterationSum[12] - iterationSum[7] == 0) {
            score = 59
                    - 2
                    * (iterationSum[7] - iterationSum[4] + 2 - iterationSum[4] - iterationSum[2])
                    + 2 * (iterationSum[4] - iterationSum[2]);
        }

        if (iterationSum[12] - iterationSum[7] == 1) {
            score = 80 - 3
                    * (iterationSum[2] + 2 - iterationSum[4] - iterationSum[2])
                    + 2 * iterationSum[4] - iterationSum[2] - 7
                    * (iterationSum[7] - iterationSum[4]);
        }
        // 40-48
        if (iterationSum[12] - iterationSum[7] == 2) {
            score = Math.abs(new Random().nextInt()) % 8 + 40;
        }
        // 30-39
        if (iterationSum[12] - iterationSum[7] == 3) {
            score = Math.abs(new Random().nextInt()) % 9 + 30;
        }
        // 20-29
        if (iterationSum[12] - iterationSum[7] == 3) {
            score = Math.abs(new Random().nextInt()) % 9 + 20;
        }
        // 0-19
        if (iterationSum[12] - iterationSum[7] >= 4) {
            score = Math.abs(new Random().nextInt()) % 19 + 0;
        }
        if (score == -1) {
            Log.e(TAG, "error in score count, no corresponding condition ");
        }
    }

}
