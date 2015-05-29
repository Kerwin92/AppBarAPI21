package com.brainstorm.neckup.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.brainstorm.neckup.R;
import com.brainstorm.neckup.Utils.SpecialCalendar;
import com.brainstorm.neckup.activity.TodayRecActivity;
import com.brainstorm.neckup.activity.Word2SayActivity;
import com.brainstorm.neckup.adapter.DateAdapter;
import com.brainstorm.neckup.adapter.HlvRecordItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kerwin on 15-5-7.
 */
public class RecordFragment extends Fragment implements View.OnClickListener, GestureDetector.OnGestureListener {
    private final String TAG = "RecordFragment";
    private final int NUMDAYS = 13;
    private List<HlvRecordItem> hlvRecordItems = new ArrayList<>();
    private TextView tvAm, tvPm, tvNews, tvView, tvLike;

    private ViewFlipper viewFlipper = null;
    private GridView gridView = null;
    private GestureDetector gestureDetector = null;
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private int week_c = 0;
    private int week_num = 0;
    private String currentDate = "";
    private static int jumpWeek = 0;
    private static int jumpMonth = 0;
    private static int jumpYear = 0;
    private DateAdapter dateAdapter;
    private int daysOfMonth = 0; // 某月的天数
    private int dayOfWeek = 0; // 具体某一天是星期几
    private int weeksOfMonth = 0;
    private SpecialCalendar sc = null;
    private boolean isLeapyear = false; // 是否为闰年
    private int selectPostion = 0;
    private String dayNumbers[] = new String[7];
    //    private TextView tvDate;
    private int currentYear;
    private int currentMonth;
    private int currentWeek;
    private int currentDay;
    private int currentNum;
    private boolean isStart;// 是否是交接的月初
    private int rightCounts = 0, leftCounts = 0;

    /**/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedBundleInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        getDate();
        initView(view);
        addData();
        gestureDetector = new GestureDetector(this);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.flipper);
        dateAdapter = new DateAdapter(getActivity(), getResources(), currentYear,
                currentMonth, currentWeek, currentNum, selectPostion,
                currentWeek == 1 ? true : false, hlvRecordItems, leftCounts, rightCounts);
        addGridView();
        dayNumbers = dateAdapter.getDayNumbers();
        gridView.setAdapter(dateAdapter);
        selectPostion = dateAdapter.getTodayPosition();
        gridView.setSelection(selectPostion);
        viewFlipper.addView(gridView, 0);
        return view;
    }

    private void initView(View view) {
        tvAm = (TextView) view.findViewById(R.id.tv_am);
        tvPm = (TextView) view.findViewById(R.id.tv_pm);
        tvNews = (TextView) view.findViewById(R.id.tv_news);
        tvLike = (TextView) view.findViewById(R.id.tv_likeCount);
        tvView = (TextView) view.findViewById(R.id.tv_view);
        tvNews.setOnClickListener(this);
        tvView.setOnClickListener(this);
    }

    private void addData() {
        for (int i = 0; i < NUMDAYS; i++) {
            HlvRecordItem hlvRecordItem = new HlvRecordItem();
            hlvRecordItem.setDate("0" + i + "/" + "05");
            hlvRecordItem.setAmDone(i % 2);
            hlvRecordItem.setPmDone(i % 2);
            hlvRecordItem.setComment(i + "我脖颈疼，tmd");
            hlvRecordItem.setAmExec(i + "操");
            hlvRecordItem.setPmExec(i + "操");
            hlvRecordItem.setNewsTitle(i + "我不是新闻");
            hlvRecordItem.setLikeCount(i);
            hlvRecordItems.add(hlvRecordItem);
        }
    }

    private void addRecordInfo(int position) {
        HlvRecordItem hlvRecordItem = new HlvRecordItem();
        hlvRecordItem = hlvRecordItems.get(position);
        tvAm.setText(hlvRecordItem.getAmExec());
        tvPm.setText(hlvRecordItem.getPmExec());
        Drawable drawableAm = getResources().getDrawable(R.drawable.exec_pm);
        drawableAm.setBounds(0, 0, drawableAm.getIntrinsicWidth(), drawableAm.getIntrinsicHeight()); //必须设置图片大小，否则不显示
        Drawable drawablePm = getResources().getDrawable(R.drawable.exec_done);
        drawablePm.setBounds(0, 0, drawablePm.getIntrinsicWidth(), drawablePm.getIntrinsicHeight()); //必须设置图片大小，否则不显示

        if (hlvRecordItem.getAmDone() == 1) {
            tvAm.setCompoundDrawables(drawableAm, null, null, null);
        } else {
            tvAm.setCompoundDrawables(drawablePm, null, null, null);
        }
        if (hlvRecordItem.getPmDone() == 1) {
            tvPm.setCompoundDrawables(drawableAm, null, null, null);
        } else {
            tvPm.setCompoundDrawables(drawablePm, null, null, null);
        }
        tvNews.setText(hlvRecordItem.getNewsTitle());
        tvView.setText(hlvRecordItem.getComment());
        tvLike.setText(String.valueOf(hlvRecordItem.getLikeCount()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_news:
                Intent intent_news = new Intent(getActivity(), TodayRecActivity.class);
                startActivity(intent_news);
                break;
            case R.id.tv_view:
                Intent intent_view = new Intent(getActivity(), Word2SayActivity.class);
                startActivity(intent_view);
                break;
            default:
                break;
        }
    }

    /**
     * 获取当前日期
     */
    private void getDate() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date);
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);
        currentYear = year_c;
        currentMonth = month_c;
        currentDay = day_c;
        sc = new SpecialCalendar();
        getCalendar(year_c, month_c);
        week_num = getWeeksOfMonth();
        currentNum = week_num;
        if (dayOfWeek == 7) {
            week_c = day_c / 7 + 1;
        } else {
            if (day_c <= (7 - dayOfWeek)) {
                week_c = 1;
            } else {
                if ((day_c - (7 - dayOfWeek)) % 7 == 0) {
                    week_c = (day_c - (7 - dayOfWeek)) / 7 + 1;
                } else {
                    week_c = (day_c - (7 - dayOfWeek)) / 7 + 2;
                }
            }
        }
        currentWeek = week_c;
        getCurrent();


    }

    /**
     * 判断某年某月所有的星期数
     *
     * @param year
     * @param month
     */
    public int getWeeksOfMonth(int year, int month) {
        // 先判断某月的第一天为星期几
        int preMonthRelax = 0;
        int dayFirst = getWhichDayOfWeek(year, month);
        int days = sc.getDaysOfMonth(sc.isLeapYear(year), month);
        if (dayFirst != 7) {
            preMonthRelax = dayFirst;
        }
        if ((days + preMonthRelax) % 7 == 0) {
            weeksOfMonth = (days + preMonthRelax) / 7;
        } else {
            weeksOfMonth = (days + preMonthRelax) / 7 + 1;
        }
        return weeksOfMonth;

    }

    /**
     * 判断某年某月的第一天为星期几
     *
     * @param year
     * @param month
     * @return
     */
    public int getWhichDayOfWeek(int year, int month) {
        Log.i(TAG, "sc: " + sc);
        return sc.getWeekdayOfMonth(year, month);

    }

    /**
     * @param year
     * @param month
     */
    public int getLastDayOfWeek(int year, int month) {
        return sc.getWeekDayOfLastMonth(year, month,
                sc.getDaysOfMonth(isLeapyear, month));
    }

    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year); // 是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
    }

    public int getWeeksOfMonth() {
        // getCalendar(year, month);
        int preMonthRelax = 0;
        if (dayOfWeek != 7) {
            preMonthRelax = dayOfWeek;
        }
        if ((daysOfMonth + preMonthRelax) % 7 == 0) {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7;
        } else {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7 + 1;
        }
        return weeksOfMonth;
    }

    private void addGridView() {
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        gridView = new GridView(getActivity());
        gridView.setNumColumns(7);
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.i(TAG, "day:" + dayNumbers[position]);

                /*计算点击的item位置和当天位置的偏移量, dateAdapter.getTodayPosition()为当前页面偏移量，7-position为点击页的偏移量*/
                int dPosition = 0;
                Log.i(TAG, "latestDay not executed: " + (7 - (hlvRecordItems.size() - (leftCounts - rightCounts) * 7 - dateAdapter.getTodayPosition() - 1)));
                Log.i(TAG, "getTodayPosition: " + dateAdapter.getTodayPosition());
                /*需要判断是否晚于当天*/
                if (leftCounts == rightCounts && position > dateAdapter.getTodayPosition()) {
                    Toast.makeText(getActivity(), "噢，还没到那一天", Toast.LENGTH_SHORT).show();
                    /*需要判断是否早于最早开始的一天:页面位于最早的一天，切点击的日期位于最早一天之前*/
                } else if (leftCounts - rightCounts == hlvRecordItems.size() / 7 && position < 7 - (hlvRecordItems.size() - (leftCounts - rightCounts - 1) * 7 - (dateAdapter.getTodayPosition() + 1))) {
                    Toast.makeText(getActivity(), "噢，那天还没开始用app呢", Toast.LENGTH_SHORT).show();
                } else {
                    /*当天显示在页面*/
                    if (leftCounts == rightCounts) {
                        dPosition = dateAdapter.getTodayPosition() - position;
                    } else {
                        dPosition = (leftCounts - rightCounts - 1) * 7 + dateAdapter.getTodayPosition() + 7 - position;
                    }
                    Log.i(TAG, "dPosition: " + dPosition);
                    if (dPosition < hlvRecordItems.size()) {
                        addRecordInfo(hlvRecordItems.size() - dPosition - 1);
                    }
                    /*有效的日期点击才传参至adapter*/
                    selectPostion = position;
                    Log.i(TAG, "selectPosition: " + selectPostion);
                    dateAdapter.setSeclection(selectPostion);
                    dateAdapter.notifyDataSetChanged();
                }
            }
        });
        gridView.setLayoutParams(params);
    }

    @Override
    public void onPause() {
        super.onPause();
        jumpWeek = 0;
    }

    @Override
    public boolean onDown(MotionEvent e) {

        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 重新计算当前的年月
     */
    public void getCurrent() {
        if (currentWeek > currentNum) {
            if (currentMonth + 1 <= 12) {
                currentMonth++;
            } else {
                currentMonth = 1;
                currentYear++;
            }
            currentWeek = 1;
            currentNum = getWeeksOfMonth(currentYear, currentMonth);
        } else if (currentWeek == currentNum) {
            if (getLastDayOfWeek(currentYear, currentMonth) == 6) {
            } else {
                if (currentMonth + 1 <= 12) {
                    currentMonth++;
                } else {
                    currentMonth = 1;
                    currentYear++;
                }
                currentWeek = 1;
                currentNum = getWeeksOfMonth(currentYear, currentMonth);
            }

        } else if (currentWeek < 1) {
            if (currentMonth - 1 >= 1) {
                currentMonth--;
            } else {
                currentMonth = 12;
                currentYear--;
            }
            currentNum = getWeeksOfMonth(currentYear, currentMonth);
            currentWeek = currentNum - 1;
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        int gvFlag = 0;
        //        如果没有左滑过，择不能右滑。而且右滑和左滑的相对次数不能超多天数
        if (e1.getX() - e2.getX() > 80 && leftCounts > 0 && rightCounts < leftCounts) {
            // 向左滑，显示右边
            addGridView();
            currentWeek++;
            getCurrent();
            dateAdapter = new DateAdapter(getActivity(), getResources(), currentYear,
                    currentMonth, currentWeek, currentNum, selectPostion,
                    currentWeek == 1 ? true : false, hlvRecordItems, leftCounts, rightCounts);
            dayNumbers = dateAdapter.getDayNumbers();
            gridView.setAdapter(dateAdapter);
            gvFlag++;
            rightCounts++;
            Log.i(TAG, "rightCounts: " + rightCounts + "leftCounts: " + leftCounts);
            viewFlipper.addView(gridView, gvFlag);
            /*记录之前选中的页面位置，没有必要*/
            if (dateAdapter.getCurrentYear(selectPostion) == currentYear && dateAdapter.getCurrentMonth(selectPostion) == currentMonth && Integer.parseInt(dayNumbers[selectPostion]) == currentDay) {
                dateAdapter.setSeclection(selectPostion);
            }
            this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_left_in));
            this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_left_out));
            this.viewFlipper.showNext();
            viewFlipper.removeViewAt(0);
            return true;
            //        左滑右滑的相对次数不能超过
        } else if (e1.getX() - e2.getX() < -80 && leftCounts - rightCounts < hlvRecordItems.size() / 7 && leftCounts - rightCounts >= 0) {
            // 向右滑，显示左边
            addGridView();
            currentWeek--;
            getCurrent();
            dateAdapter = new DateAdapter(getActivity(), getResources(), currentYear,
                    currentMonth, currentWeek, currentNum, selectPostion,
                    currentWeek == 1 ? true : false, hlvRecordItems, leftCounts, rightCounts);
            dayNumbers = dateAdapter.getDayNumbers();
            gridView.setAdapter(dateAdapter);
            //            tvDate.setText(dateAdapter.getCurrentYear(selectPostion) + "年"
            //                    + dateAdapter.getCurrentMonth(selectPostion) + "月"
            //                    + dayNumbers[selectPostion] + "日");
            gvFlag++;
            leftCounts++;
            Log.i(TAG, "leftCounts: " + leftCounts + "rightCounts: " + rightCounts);
            viewFlipper.addView(gridView, gvFlag);
            /*记录之前选中的页面位置，没有必要*/
            if (dateAdapter.getCurrentYear(selectPostion) == currentYear && dateAdapter.getCurrentMonth(selectPostion) == currentMonth && Integer.parseInt(dayNumbers[selectPostion]) == currentDay) {
                dateAdapter.setSeclection(selectPostion);
            }
            this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_right_in));
            this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                    R.anim.push_right_out));
            this.viewFlipper.showPrevious();
            viewFlipper.removeViewAt(0);
            return true;
        } else {
            Toast.makeText(getActivity(), "不能再滑了，已经到头。。。", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    protected boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
