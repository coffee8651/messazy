package com.coffee.messzay.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;

import java.util.ArrayList;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by coffee on 2015/9/20.
 */
public class MyPieChart extends PieChartView {
    public MyPieChart(Context context) {
        super(context);
    }

    public MyPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static PieChartData updateDummyData(int item) {
        boolean numValues = true;
        PieChartData data = new PieChartData();
        ArrayList values = new ArrayList(4);
        switch (item) {
            case 0:
                values.add(new SliceValue(40.0F, Color.BLUE));
                values.add(new SliceValue(20.0F, Color.GREEN));
                values.add(new SliceValue(30.0F));
                values.add(new SliceValue(50.0F));
                break;
            case 1:
                values.add(new SliceValue(40.0F, Color.BLUE));
                values.add(new SliceValue(20.0F, Color.GREEN));
                values.add(new SliceValue(30.0F));
                values.add(new SliceValue(50.0F));
                break;
            case 2:
                values.add(new SliceValue(40.0F,  Color.BLUE));
                values.add(new SliceValue(20.0F, Color.GREEN));
                values.add(new SliceValue(30.0F, Color.RED));
                values.add(new SliceValue(50.0F));
                break;
            case 3:
                values.add(new SliceValue(40.0F,  Color.BLUE));
                values.add(new SliceValue(20.0F, Color.GREEN));
                values.add(new SliceValue(30.0F, Color.RED));
                values.add(new SliceValue(50.0F, Color.YELLOW));
                break;
        }
        data.setValues(values);
        return data;
    }


}
