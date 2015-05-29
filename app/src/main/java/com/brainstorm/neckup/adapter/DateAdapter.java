package com.brainstorm.neckup.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brainstorm.neckup.R;
import com.brainstorm.neckup.Utils.SpecialCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateAdapter extends BaseAdapter {
    private static String TAG = "ZzL";
    private boolean isLeapyear = false; // 是否为闰年
    private int daysOfMonth = 0; // 某月的天数
    private int dayOfWeek = 0; // 具体某一天是星期几
    private int nextDayOfWeek = 0;
    private int lastDayOfWeek = 0;
    private int lastDaysOfMonth = 0; // 上一个月的总天数
    private int eachDayOfWeek = 0;
    private Context context;
    private SpecialCalendar sc = null;
    private Resources res = null;
    private Drawable drawable = null;
    private String[] dayNumber = new String[7];
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    private int currentFlag = -1; // 用于标记当天
    // 系统当前时间
    private String sysDate = "";
    private String sys_year = "";
    private String sys_month = "";
    private String sys_day = "";
    private String currentYear = "";
    private String currentMonth = "";
    private String currentWeek = "";
    private String currentDay = "";
    private int weeksOfMonth;
    private int default_postion;
    private int clickTemp = -1;
    private int week_num = 0;
    private int week_c = 0;
    private int month = 0;
    private int jumpWeek = 0;
    private int c_month = 0;
    private int c_day_week = 0;
    private int n_day_week = 0;
    private boolean isStart;
    private int rightCounts = 0, leftCounts = 0;

    // 标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

    private List<HlvRecordItem> hlvRecordItems = new ArrayList<>();
    private HlvRecordItem hlvRecordItem = null;

    public DateAdapter() {

        Date date = new Date();
        sysDate = sdf.format(date); // 当期日期
        sys_year = sysDate.split("-")[0];
        sys_month = sysDate.split("-")[1];
        sys_day = sysDate.split("-")[2];
        month = Integer.parseInt(sys_month);
    }


    public DateAdapter(Context context, Resources rs, int year_c, int month_c,
                       int week_c, int week_num, int default_postion, boolean isStart, List<HlvRecordItem> hlvRecordItems, int leftCounts, int rightCounts) {
        this();
        this.context = context;
        this.res = rs;
        this.default_postion = default_postion;
        this.week_c = week_c;
        this.isStart = isStart;
        this.leftCounts = leftCounts;
        this.rightCounts = rightCounts;
        sc = new SpecialCalendar();

        lastDayOfWeek = sc.getWeekDayOfLastMonth(year_c, month_c,
                sc.getDaysOfMonth(sc.isLeapYear(year_c), month_c));
        Log.i(TAG, "week_c:" + week_c);
        currentYear = String.valueOf(year_c);
        ; // 得到当前的年份
        currentMonth = String.valueOf(month_c); // 得到本月
        // （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
        currentDay = String.valueOf(sys_day); // 得到当前日期是哪天
        getCalendar(Integer.parseInt(currentYear),
                Integer.parseInt(currentMonth));
        currentWeek = String.valueOf(week_c);
        getWeek(Integer.parseInt(currentYear), Integer.parseInt(currentMonth),
                Integer.parseInt(currentWeek));
        this.hlvRecordItems = hlvRecordItems;
    }

    public int getTodayPosition() {
        int todayWeek = sc.getWeekDayOfLastMonth(Integer.parseInt(sys_year),
                Integer.parseInt(sys_month), Integer.parseInt(sys_day));
        if (todayWeek == 7) {
            clickTemp = 0;
        } else {
            clickTemp = todayWeek;
        }
        return clickTemp;
    }

    public int getCurrentMonth(int position) {
        int thisDayOfWeek = sc.getWeekdayOfMonth(Integer.parseInt(currentYear),
                Integer.parseInt(currentMonth));
        if (isStart) {
            if (thisDayOfWeek != 7) {
                if (position < thisDayOfWeek) {
                    return Integer.parseInt(currentMonth) - 1 == 0 ? 12
                            : Integer.parseInt(currentMonth) - 1;
                } else {
                    return Integer.parseInt(currentMonth);
                }
            } else {
                return Integer.parseInt(currentMonth);
            }
        } else {
            return Integer.parseInt(currentMonth);
        }

    }

    public int getCurrentYear(int position) {
        int thisDayOfWeek = sc.getWeekdayOfMonth(Integer.parseInt(currentYear),
                Integer.parseInt(currentMonth));
        if (isStart) {
            if (thisDayOfWeek != 7) {
                if (position < thisDayOfWeek) {
                    return Integer.parseInt(currentMonth) - 1 == 0 ? Integer
                            .parseInt(currentYear) - 1 : Integer
                            .parseInt(currentYear);
                } else {
                    return Integer.parseInt(currentYear);
                }
            } else {
                return Integer.parseInt(currentYear);
            }
        } else {
            return Integer.parseInt(currentYear);
        }
    }

    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year); // 是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
        lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1);
        nextDayOfWeek = sc.getDaysOfMonth(isLeapyear, month + 1);
    }

    public void getWeek(int year, int month, int week) {
        for (int i = 0; i < dayNumber.length; i++) {
            if (dayOfWeek == 7) {
                dayNumber[i] = String.valueOf((i + 1) + 7 * (week - 1));
            } else {
                if (week == 1) {
                    if (i < dayOfWeek) {
                        dayNumber[i] = String.valueOf(lastDaysOfMonth
                                - (dayOfWeek - (i + 1)));
                    } else {
                        dayNumber[i] = String.valueOf(i - dayOfWeek + 1);
                    }
                } else {
                    dayNumber[i] = String.valueOf((7 - dayOfWeek + 1 + i) + 7
                            * (week - 2));
                }
            }

        }
    }

    public String[] getDayNumbers() {
        return dayNumber;
    }

    /**
     * 得到某月有几周(特殊算法)
     */
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

    /**
     * 某一天在第几周
     */
    public void getDayInWeek(int year, int month) {

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dayNumber.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_calendar, null);
            viewHolder.tvCalendar = (TextView) convertView
                    .findViewById(R.id.tv_calendar);
            viewHolder.ivAm = (ImageView) convertView.findViewById(R.id.iv_am);
            viewHolder.ivPm = (ImageView) convertView.findViewById(R.id.iv_pm);
            viewHolder.llytRecord = (LinearLayout) convertView.findViewById(R.id.llyt_record);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*如果item时间不在用户使用期间内*/
        viewHolder.tvCalendar.setText(dayNumber[position] + "/" + String.valueOf(getCurrentMonth(position)));
        if ((leftCounts == rightCounts && position > getTodayPosition()) || (leftCounts == rightCounts && position < getTodayPosition() - hlvRecordItems.size()) || (leftCounts - rightCounts == hlvRecordItems.size() / 7 && position < 7 - (hlvRecordItems.size() - (leftCounts - rightCounts - 1) * 7 - (getTodayPosition() + 1)))) {

        } else {
            hlvRecordItem = hlvRecordItems.get(position);
            //            viewHolder.tvCalendar.setText(dayNumber[position] + "/" + String.valueOf(getCurrentMonth(position)));
            Log.i(TAG, "clickTemp: " + clickTemp);
            if (clickTemp == position) {
                viewHolder.tvCalendar.setSelected(true);
                viewHolder.tvCalendar.setTextColor(Color.WHITE);
                //            viewHolder.tvCalendar.setBackgroundResource(R.drawable.circle_message);
                viewHolder.llytRecord.setBackgroundColor(context.getResources().getColor(R.color.darker_blue));
            } else {
                viewHolder.tvCalendar.setSelected(false);
                viewHolder.tvCalendar.setTextColor(Color.BLACK);
                viewHolder.tvCalendar.setBackgroundColor(Color.TRANSPARENT);
                viewHolder.llytRecord.setBackgroundColor(context.getResources().getColor(R.color.light_blue));
            }
            if (hlvRecordItem.getAmDone() == 1) {
                viewHolder.ivAm.setImageResource(R.drawable.hlv_done);
            } else {
                viewHolder.ivAm.setImageResource(R.drawable.hlv_pm);
            }
            if (hlvRecordItem.getPmDone() == 1) {
                viewHolder.ivPm.setImageResource(R.drawable.hlv_done);
            } else {
                viewHolder.ivPm.setImageResource(R.drawable.hlv_pm);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView tvCalendar;
        private ImageView ivAm;
        private ImageView ivPm;
        private LinearLayout llytRecord;
    }
}
