package com.coffee.messzay;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.coffee.messzay.R;
import com.coffee.messzay.widget.MyPieChart;

import java.util.ArrayList;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

public class ClassfiyActivity extends ActionBarActivity {
    private MyPieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classfiy);

        init();

    }

    private void init() {
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int [] iconBtn = {R.id.canyin
                , R.id.jiaotong, R.id.zhufang, R.id.gouwu};
        pieChart = (MyPieChart)findViewById(R.id.my_pie_chart);

        for(int i=0;i<iconBtn.length;i++) {
            final ImageView imgview = (ImageView)findViewById(iconBtn[i]);
            imgview.setTag(""+i);
            imgview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int current = Integer.parseInt((String)imgview.getTag());
                    pieChart.setPieChartData(updateDummyData(current));
                }
            });
        }


    }


    public PieChartData updateDummyData(int item) {
        boolean numValues = true;
        PieChartData data = new PieChartData();
        ArrayList values = new ArrayList(4);
        switch (item) {
            case 0:
                values.add(new SliceValue(40.0F, Color.BLUE));
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
