package com.brainstorm.neckup.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.brainstorm.neckup.R;
import com.brainstorm.neckup.adapter.HealthAssessAdapter;
import com.brainstorm.neckup.database.DBManager;
import com.brainstorm.neckup.database.QuestionDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/5/18.
 */
public class HealthAssessActivity extends Activity implements View.OnClickListener {
    private final String TAG = "HealthAssessActivity";
    public static final int QUESTION_NUM = 13;
    public static final String USERPREFERENCE = "userpreference";
    private final String QUESTIONCLICK = "question_click";
    private Button btnFinishTest;
    private ListView list_custom;
    private HealthAssessAdapter listCustomAdapter;
    private QuestionDao question_dao;


    public DBManager dbHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_healthassess);
        initPreference();
        initDatebase();
        initView();
    }

    /**
     * 初始化数据库
     */
    //    改过代码
    private void initPreference() {
        sharedPreferences = getSharedPreferences(
                USERPREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void initView() {
        btnFinishTest = (Button) findViewById(R.id.btn_wanchengceshi);
        list_custom = (ListView) findViewById(R.id.lv_custom_feeling);
        List<HashMap<String, Object>> listCustoms = new ArrayList<HashMap<String, Object>>();
        Cursor cursor = question_dao.showQuestion();
        int i = 0;

        while (cursor.moveToNext() && i < QUESTION_NUM) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("qname", cursor.getString(cursor.getColumnIndex("qname")));
            Log.i(TAG, "cursor" + cursor.getString(cursor.getColumnIndex("qname")));
            listCustoms.add(map);
            i++;
        }
        cursor.close();
        listCustomAdapter = new HealthAssessAdapter(this, listCustoms);
        list_custom.setAdapter(listCustomAdapter);
        setCheckbox();
        btnFinishTest.setOnClickListener(this);
    }

    private void initDatebase() {
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        dbHelper.closeDatabase();
        question_dao = new QuestionDao(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_wanchengceshi:
                Boolean clicked = false;
                int qArray[] = new int[QUESTION_NUM];
                for (int k = 0; k < QUESTION_NUM; k++) {
                    if (listCustomAdapter.state.get(k) != null
                            && listCustomAdapter.state.get(k) == true) {
                        qArray[k] = 1;
                        clicked = true;
                    }
                }
                storeCheckbox(qArray);
                if (clicked) {
                    Intent intent = new Intent();
                    intent.setClass(this, MainActivity.class);
                    intent.putExtra("qArray", qArray);
                    MainActivity.FROMACTIVITY = 1;
                    /*表示由问卷调查进入主页面*/
                    Log.i(TAG, "qArray: " + qArray);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "您还没勾选选项", Toast.LENGTH_LONG).show();
                }
                /*// 完成HealthAssessFragment以后显示AssessFragment页面
                editor.putInt("from", 1);
                editor.commit();*/
                break;
            default:
                break;
        }
    }

    /**
     * 将用户的checkbox点击情况存入sharedPreference数据库
     *
     * @param qArray
     */
    private void storeCheckbox(int[] qArray) {
        String string = IntArray2String(qArray);
        Log.i(TAG, "string will be stored in preference: " + string);
        editor.putString(QUESTIONCLICK, string);
        editor.commit();
    }

    /**
     * 将整形数组中的元素取出后拼接成字符串
     *
     * @param qArray
     * @return string
     */
    private String IntArray2String(int[] qArray) {
        String string = "";
        for (int element : qArray) {
            string += String.valueOf(element);
        }
        return string;
    }


    /**
     * 设置checkbox的点击情况
     */
    private void setCheckbox() {
        int checkboxArray[] = new int[QUESTION_NUM];
        // 复制string2Int数组的全部值给checkboxArray
        System.arraycopy(string2Int(), 0, checkboxArray, 0, QUESTION_NUM);
        for (int j = 0; j < checkboxArray.length; j++) {
            Log.i(TAG, "checkboxArray[" + j + "]: " + checkboxArray[j]);
            if (checkboxArray[j] == 1) {
                listCustomAdapter.getIsSelected().put(j, true);
            }
        }
    }

    /**
     * 将字符串中的字符分割后附值给整形数组
     */
    private int[] string2Int() {
        // 如果尚未存放数据至preference，则默认每个checkbox均未选择
        String string = sharedPreferences.getString(QUESTIONCLICK,
                "0000000000000");
        Log.i(TAG, "string got from preference: " + string);
        int getCheckboxArray[] = new int[QUESTION_NUM];
        for (int i = 0; i < string.toCharArray().length; i++) {
            // “0”的字符编码为48
            int isChosen = string.toCharArray()[i] - 48;
            Log.i(TAG, "isChosen: " + isChosen);
            getCheckboxArray[i] = isChosen;
        }
        return getCheckboxArray;
    }

}
