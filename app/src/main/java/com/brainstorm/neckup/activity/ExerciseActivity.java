package com.brainstorm.neckup.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.brainstorm.neckup.R;
import com.brainstorm.neckup.Utils.ExcRoundProgressBar;
import com.brainstorm.neckup.Utils.GifMovieView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/5/19.
 */
public class ExerciseActivity extends Activity implements View.OnClickListener{
    private  final  String TAG="ExerciseActivity";
    private Boolean firstOpen = true, isFirst = true;
    private GifMovieView gif;
    private ImageButton ibtnPlay,ibtnBack,ibtnSound;
    private ExcRoundProgressBar mExcRoundProgressBar;
    private Handler handler;
    private Timer timer;
    private int msg_what=0;
    // 电源控制管理器，比如防锁屏
    PowerManager mPowerManager;
    // 唤醒锁
    PowerManager.WakeLock mWakeLock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exercise);
//        点亮屏幕150秒
        acquireWakeLock(150000);
//        解锁屏幕
        disableKeyguard(true);
        initView();
    }
    /**
     * 初始化界面
     * */

    private void initView(){
        gif=(GifMovieView)findViewById(R.id.gif);
        gif.setMovieResource(R.raw.jizhuichao01);
        ibtnBack=(ImageButton)findViewById(R.id.ibtn_back);
        ibtnSound=(ImageButton)findViewById(R.id.ibtn_sound);
        ibtnPlay=(ImageButton)findViewById(R.id.ibtn_play);
        mExcRoundProgressBar=(ExcRoundProgressBar)findViewById(R.id.excRoundProgress);
        mExcRoundProgressBar.setRoundProgressColor(getResources().getColor(R.color.round_progress_color));
        mExcRoundProgressBar.setRoundtColor(getResources().getColor(R.color.round_color));
        mExcRoundProgressBar.setTextIsDisplayable(false);
        mExcRoundProgressBar.setRoundWidth(10);
        mExcRoundProgressBar.setMax(100);
        refreshPlayProgress();
//         初始化不自动播放
        if (firstOpen){
            gif.setPaused(true);
            firstOpen=false;
        }
        ibtnPlay.setOnClickListener(this);
        ibtnSound.setOnClickListener(this);
        ibtnBack.setOnClickListener(this);
    }
    /**
      *刷新做操进度条
     *  */
 private void refreshPlayProgress(){
     handler=new Handler(){
         @Override
         public void handleMessage(Message msg) {
             msg_what=msg.what;
             mExcRoundProgressBar.setProgress(msg.what);
         }
     };
 }
      @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.ibtn_play:
                  gif.setPaused(!gif.isPaused());
                  if(!gif.isPaused()){
                      timer=new Timer();
                      timer.schedule(new TimerTask() {
                          int i=msg_what;
                          @Override
                          public void run() {
                              Message msg=new Message();
                              i=i+1;
                              msg.what=i;
                              handler.sendMessage(msg);
                          }
                      },0,100);

                  }
          }

    }

    // 解锁屏幕
    @SuppressWarnings("deprecation")
    private void disableKeyguard(boolean isDis) {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        @SuppressWarnings("deprecation")
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("123");
        if (isDis) {
            // 解锁
            keyguardLock.disableKeyguard();
        } else {
            // 屏幕锁定
            keyguardLock.reenableKeyguard();
        }
    }
    /*
     * 关闭屏幕
     */
    public void releaseWakeLock() {
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }
    /*
     * 点亮屏幕
     */
    public void acquireWakeLock(long milltime) {
        if (mWakeLock == null) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                    | PowerManager.ACQUIRE_CAUSES_WAKEUP
                    | PowerManager.ON_AFTER_RELEASE, TAG);
            mWakeLock.acquire(milltime);
        }
    }
    /*
     * 屏幕保持常亮
     */
    private void screenWakeUp(boolean isWakeUp) {
        if (null == mPowerManager) {
            mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        }
        if (null == mWakeLock) {
            mWakeLock = mPowerManager.newWakeLock(
                    PowerManager.SCREEN_DIM_WAKE_LOCK, getClass().getName());
        }
        if (!mWakeLock.isHeld() && isWakeUp) {
            mWakeLock.acquire();
        }
        if (mWakeLock.isHeld() && !isWakeUp) {
            mWakeLock.release();
        }
    }
}
