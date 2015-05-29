package com.brainstorm.neckup.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.HorizontalScrollView;

import com.brainstorm.neckup.R;
import com.brainstorm.neckup.Utils.TextViewVertical;

/**
 * Created by kerwin on 15-5-15.
 */
public class TodayRecActivity extends Activity {
    private TextViewVertical textViewVertical;
    private HorizontalScrollView horizontalScrollView;

    @Override
    protected void onCreate(Bundle savedBundleInstanceState) {
        super.onCreate(savedBundleInstanceState);
        setContentView(R.layout.activity_todayrec);
        textViewVertical = (TextViewVertical) findViewById(R.id.tv);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.hscl);
        Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case TextViewVertical.LAYOUT_CHANGED:
                        //                        horizontalScrollView.scrollBy(textViewVertical.getTextWidth(), 0);// 滚动到最右边
                        break;
                }
            }
        };
        textViewVertical.setHandler(handler);// 将Handler绑定到TextViewVertical

        textViewVertical.setTextSize(40);
        textViewVertical.setTextColor(Color.BLACK);
        textViewVertical.setText("5月13日，暴风科技（300431.SZ）再度涨停，股价达到252.86元，市值达到303.43亿元（约48.92亿美元）。" +

                        "5月15日，暴风科技以每股226元收盘，下跌6.5%，市值为271.20亿元（约43.68亿美元）。" +

                        "暴风科技在接受《中国经营报》记者采访时表示：“股价的情况由市场来决定，目前也无法做出太多预估。”" +

                        "而在艾媒咨询集团董事长兼CEO、广东省互联网协会副会长张毅看来，连续涨停的根本原因，还是在于中国股民对二级市场压抑太久。" +
                        "“原来很少有跟前沿科技趋势结合比较好的企业，一旦有代表先进生产力概念的股票上线之后，都涨得很厉害，说明股民对它们的期待很高" +
                        "。但反过来，暴风科技的业绩下滑或者增长乏力，价格和价值两者出现偏差。”"
                        + "而4月24日暴风科技发布的2015年第一季度财报显示，公司营业收入为9240.88万元，同比增长24.24%；但归属上市公司普通股东的净利润亏损320.85" +
                        "万元，同比下降146.72%。对此，公司表示，净利润下降的主要原因，是虚拟现实业务处于早期大规模投入阶段，导致公司一季度整体亏损。"
        );
    }
}
