package com.brainstorm.neckup.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.brainstorm.neckup.R;
import com.brainstorm.neckup.adapter.CommentAdapter;
import com.brainstorm.neckup.adapter.CommentItem;

import java.util.ArrayList;

/**
 * Created by kerwin on 15-5-18.
 */
public class Word2SayActivity extends Activity implements View.OnClickListener, OnScrollListener {
    private final String TAG = "Word2SayActivity";
    private RadioButton rbtnTime, rbtnLike;
    private ListView lvComment;
    private EditText etComment;
    private ImageView ivSend;
    private CommentAdapter commentAdapter;
    private ArrayList<CommentItem> commentItems = new ArrayList<>();
    private Boolean state = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word2say);
        initView();
        initListView(lvComment);
    }


    /*初始化界面*/
    private void initView() {
        lvComment = (ListView) findViewById(R.id.lv_comment);
        etComment = (EditText) findViewById(R.id.et_comment);
        ivSend = (ImageView) findViewById(R.id.iv_send);
        lvComment.setOnScrollListener(this);
        etComment.setOnClickListener(this);
        ivSend.setOnClickListener(this);
    }

    /*初始化listview数据*/
    private void initListView(ListView listView) {
        addData(commentItems);
        commentAdapter = new CommentAdapter(commentItems, getApplicationContext());
        listView.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_send:
                String string = etComment.getText().toString().trim();
                if (string.length() == 0) {
                    Toast.makeText(getApplicationContext(), "您还没有输入任何内容", Toast.LENGTH_SHORT).show();
                } else {
                    /*关闭键盘*/
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etComment.getWindowToken(), 0);
                    etComment.setText(null);
                }
                break;
        }
    }

    /**
     * 加载更多事件的真正任务
     */
    //    private class MyLoadTask extends AsyncTask<Void, Void, Void> {
    //        @Override
    //        protected Void doInBackground(Void... params) {
    //            sendGetArticleRequest(0);
    //            try {
    //                Thread.sleep(2000);
    //            } catch (InterruptedException e) {
    //                e.printStackTrace();
    //            }
    //            return null;
    //        }
    //
    //        @Override
    //        protected void onPostExecute(Void aVoid) {
    //            addData();
    //            commentAdapter.notifyDataSetChanged();
    //        }
    //    }

    /**
     * 向服务端请求用户发布的状态
     *
     * @param id
     * @return
     */
    //    private void sendGetArticleRequest(final long id) {
    //        String path = null;
    //        List<NameValuePair> params = new ArrayList<NameValuePair>();
    //        params.add(new BasicNameValuePair("id", String.valueOf(id)));
    //        String response = HttpUtils.sendGetMessage(path, params, "utf8");
    //        if (response != null) {
    //            GetAllStatusJson getAllStatusJson = GsonUtils.parseArticleJson(response);
    //            if (getAllStatusJson != null) {
    //                allStatus = getAllStatusJson.getParam().getArticle_catalog();
    //            }
    //        }
    //    }

    /**
     * 向listview加入数据
     */
    //    private void addData(List<CommentItem> allComments) {
    //        /*清除原有数据*/
    //        commentLikeItems.clear();
    //        for (int i = 0; i < allComments.size(); i++) {
    //            CommentItem commentItem = new CommentItem();
    //            commentItem.setIvHead(allComments.get(i).getIvHead());
    //            commentItem.setIvLike(allComments.get(i).getIvLike());
    //            commentItem.setTvComment(allComments.get(i).getTvComment());
    //            commentItem.setTvNickname(allComments.get(i).getTvNickname());
    //            commentItem.setTvTime(allComments.get(i).getTvTime());
    //            commentItem.setTvCount(allComments.get(i).getTvCount());
    //            commentLikeItems.add(commentItem);
    //
    //        }
    //    }
    private void addData(ArrayList<CommentItem> commentItems) {
        commentItems.clear();
        for (int i = 10; i < 20; i++) {
            CommentItem commentItem = new CommentItem();
            commentItem.setIvHead("http://www.baidu.com");
            commentItem.setIvLike(0);
            commentItem.setTvComment("为了开发天天项上，我牺牲了我的脖颈");
            commentItem.setTvNickname("码农");
            commentItem.setTvTime("14:46 2015.05.18");
            commentItem.setTvCount(i + 6);
            commentItem.setIsShow(0);
            commentItems.add(commentItem);
        }
    }

    @Override
    public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        if (SCROLL_STATE_IDLE == 0
                && lvComment.getLastVisiblePosition() == (9)
                && state) {
            state = false;
            isNetConnecting();
        }

    }

    public void isNetConnecting() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(Word2SayActivity.this, "当前无网络 , >_< 无法加载更多", Toast.LENGTH_SHORT)
                    .show();
            error();
        } else {
            Toast.makeText(Word2SayActivity.this, "=_= 正在努力加载 ...", Toast.LENGTH_SHORT)
                    .show();
            delay();
        }
    }

    private void error() {
        new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                }
                state = true;
            }
        }.start();
    }

    private void delay() {
        new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {

                }

                update();
            }

        }.start();
    }

    private void update() {
        runOnUiThread(new Runnable() {
            public void run() {

            }
        });
    }
}
