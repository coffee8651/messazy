package com.coffee.messzay.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

import com.coffee.messzay.util.DensityUtil;

/**
 * Created by coffee on 2015/9/19.
 */
public class ProgressBarWithText extends ProgressBar {
    private String text;
    private Paint mPaint;
    private int progress;

    public ProgressBarWithText(Context context) {
        super(context);
        initText(context);
    }

    public ProgressBarWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initText(context);
    }

    public ProgressBarWithText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initText(context);
    }

    @Override
    public void setProgress(int proress) {
        super.setProgress(proress);
        this.progress = proress;
        setText(proress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = 10;
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }

    private void initText(Context context) {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(Color.WHITE);
        this.mPaint.setTextSize(DensityUtil.dip2px(context, 18));
    }

    public void setText(int detailnum) {
//        int i = (int)((progress * 1.0f / this.getMax()) * 100);
        Log.e("rgf","detailnum=" + detailnum);
        this.text = String.valueOf(detailnum);
    }

}
