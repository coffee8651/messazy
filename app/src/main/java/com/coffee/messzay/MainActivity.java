package com.coffee.messzay;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import com.coffee.messzay.adapter.MainPagerAdapter;
import com.coffee.messzay.data.DBMessageLoader;
import com.coffee.messzay.data.MessazyDataMgr;
import com.coffee.messzay.vo.MessazyInfo;
import com.coffee.messzay.vo.MessazyInfoWrap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener {
    private boolean isWeekShow;
    private ArrayList<MainFragment> arr;
    private MainFragment mFragment;
    private int curMonth;
    private int curWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBMessageLoader.getInstance().load(this, new DBMessageLoader.OnLoadCallback() {

            @Override
            public void onLoadFinish() {
                //MessazyInfoWrap wrap = MessazyDataMgr.getInstance().getExpenditureByMonth(8);
                MessazyInfoWrap wrap = MessazyDataMgr.getInstance().getExpenditureByWeek(8, 4);
                if (wrap != null) {
                    Log.e("00000", "expenditureOfATM=" + wrap.expenditureOfATM);
                    Log.e("00000", "totalExpenditure=" + wrap.totalExpenditure);
                    List<MessazyInfo> infos = wrap.infos;

                    for (MessazyInfo info : infos) {
                        Log.e("00000", info.toString());
                    }
                }
                handler.sendEmptyMessage(0);
            }
        });
    }

    public void initMonth() {
        final Calendar cal = Calendar.getInstance();
        int mMonth = cal.get(Calendar.MONTH);
        mMonth++;
        if(arr == null) {
            arr = new ArrayList<>();
        } else {
            arr.clear();
        }

//        ArrayList<MainFragment> arrMonth = new ArrayList<>();

        for(int i=1;i<=mMonth;i++) {
            arr.add(new MainFragment());
        }
        for(int i=1;i<=mMonth;i++) {
            Bundle bundle = new Bundle();
            bundle.putInt(Comment.curMonthOrWeek, i);
            bundle.putBoolean(Comment.isWeekShow, false);
            arr.get(i-1).setArguments(bundle);
        }
        ViewPager mViewPager = (ViewPager)findViewById(R.id.main_pager);
        mViewPager.removeAllViews();
        mViewPager.removeAllViewsInLayout();
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), arr);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(mMonth-1);
        isWeekShow = false;
    }

    public void initWeek() {
        final Calendar cal = Calendar.getInstance();
        int mWeek = cal.get(Calendar.WEEK_OF_MONTH);
        if(arr == null) {
            arr = new ArrayList<>();
        } else {
            arr.clear();
        }

//        ArrayList<MainFragment> arrWeek = new ArrayList<>();
        for(int i=1;i<=mWeek;i++) {
            arr.add(new MainFragment());
        }

        for(int i=1;i<=mWeek;i++) {
            Bundle bundle = new Bundle();
            bundle.putInt(Comment.curMonthOrWeek, i);
            bundle.putBoolean(Comment.isWeekShow,true);
            arr.get(i - 1).setArguments(bundle);
        }

        ViewPager mViewPager = (ViewPager)findViewById(R.id.main_pager);
        mViewPager.removeAllViews();
        mViewPager.removeAllViewsInLayout();
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), arr);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(mWeek - 1);
        isWeekShow = true;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            initWeek();
        }
    };


    public void startNewActivity() {
        Intent intent = new Intent(this, ClassfiyActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
