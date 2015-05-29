package com.brainstorm.neckup.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class TextViewVertical extends View {
    private final String TAG = "TextViewVertical";
    public static final int LAYOUT_CHANGED = 1;
    private String text;
    /*画笔*/
    private Paint paint = new Paint();
    /*声明路径对象数组*/
    private Path[] paths = new Path[2];
    /*文本长度*/
    private int TextLength;
    /*文字宽度*/
    private int mTextWidth;
    /*字体大小*/
    private float mFontSize;
    /*行宽*/
    private int mLineWidth;
    /*每列文字高度*/
    private int mLineHeight;
    /*每列文字数*/
    private int numWords;
    /*屏幕宽度*/
    private int w_screen;
    /*屏幕高度*/
    private int h_screen;
    /*字符串真实的列数*/
    private int mRealLine = 0;
    /*绘制字体高度*/
    private int mFontHeight = 0;
    private Handler mHandler;

    public TextViewVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        final float scale = context.getResources().getDisplayMetrics().density;
        w_screen = dm.widthPixels;
        h_screen = dm.heightPixels;
        mLineHeight = h_screen;
        paths[0] = new Path();
        paths[1] = new Path();
    }

    // 设置文字
    public final void setText(String text) {
        this.text = text;
        this.TextLength = text.length();
        if (mLineHeight > 0)
            GetTextInfo();
    }

    // 设置字体大小
    public final void setTextSize(float size) {
        if (size != paint.getTextSize()) {
            mFontSize = size;
            //            if (mLineHeight > 0)
            //                GetTextInfo();
        }
    }

    // 设置字体颜色
    public final void setTextColor(int color) {
        paint.setColor(color);
    }

    // 设置Handler，用以发送事件
    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 将背景填充为白色
        canvas.drawColor(Color.WHITE);
        // 初始化画笔

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(mFontSize);
        //        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i = 0; i < mRealLine; i++) {
            if (i % 2 == 0) {
                paths[0].moveTo(i * mLineWidth, 0);
                paths[0].lineTo(i * mLineWidth, h_screen);
                // 绘制路径
                canvas.drawPath(paths[0], paint);
            } else {
                paths[1].moveTo(i * mLineWidth, h_screen);
                paths[1].lineTo(i * mLineWidth, 0);
                // 绘制路径
                canvas.drawPath(paths[1], paint);
            }
            // 沿着路径绘制一段文本
            if (i != mRealLine - 1) {
                canvas.drawTextOnPath(text.substring(i * numWords, (i + 1) * numWords), paths[i % 2], 0, -10, paint);
                canvas.translate(mLineWidth, 0);
            } else {
                canvas.drawTextOnPath(text.substring(i * numWords), paths[i % 2], 0, -10, paint);

            }
        }
    }

    // 计算文字行数和总宽
    private void GetTextInfo() {
        char ch;
        int h = 0;
        paint.setTextSize(mFontSize);
        // 获得字宽
        if (mLineWidth == 0) {
            float[] widths = new float[1];
            paint.getTextWidths("正", widths);// 获取单个汉字的宽度
            mLineWidth = (int) Math.ceil(widths[0] * 1.1 + 2);
            Log.i(TAG, "mLineWidth: " + mLineWidth);
        }

        FontMetrics fm = paint.getFontMetrics();
        mFontHeight = (int) (Math.ceil(fm.descent - fm.top) * 0.9);// 获得字体高度

        // 计算文字行数
        mRealLine = 0;
        Log.i(TAG, "TextLength" + TextLength);
        for (int i = 0; i < this.TextLength; i++) {
            ch = this.text.charAt(i);
            //            暂不支持换行符，按行实现
            /*if (ch == '\n') {
                mRealLine++;
                h = 0;
            } else */
            {
                h += mFontHeight;
                if (h > this.mLineHeight) {
                    mRealLine++;// 真实的行数加一
                    i--;
                    h = 0;
                } else {
                    if (i == this.TextLength - 1) {
                        mRealLine++;// 真实的行数加一
                    }
                }
            }
        }
        //        mRealLine++;// 额外增加一行
        mTextWidth = mLineWidth * mRealLine;// 计算文字总宽度
        numWords = mLineHeight / mFontHeight;
        Log.i(TAG, "numWords: " + numWords + "mFontHeight: " + mFontHeight);
        measure(mTextWidth, getHeight());// 重新调整大小
        layout(getLeft(), getTop(), getLeft() + mTextWidth, getBottom());// 重新绘制容器
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight = measureHeight(heightMeasureSpec);
        // int measuredWidth = measureWidth(widthMeasureSpec);
        if (mTextWidth == 0)
            GetTextInfo();
        setMeasuredDimension(mTextWidth, measuredHeight);
        if (mHandler != null)
            mHandler.sendEmptyMessage(LAYOUT_CHANGED);
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = h_screen;
        //        if (specMode == MeasureSpec.AT_MOST) {
        //            result = specSize;
        //        } else if (specMode == MeasureSpec.EXACTLY) {
        //            result = specSize;
        //        }
        mLineHeight = h_screen;// 设置文本高度
        Log.i("mLineHeight: ", "" + mLineHeight);
        return result;
    }
}