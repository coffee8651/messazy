package com.coffee.messzay.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coffee.messzay.R;
import com.coffee.messzay.vo.MessazyInfo;
import com.coffee.messzay.vo.MessazyInfoWrap;
import com.coffee.messzay.widget.ProgressBarWithText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by coffee on 2015/9/19.
 */
public class DetailListAdapter extends BaseAdapter {
    private boolean isWeekShow;
    private int curWeekDay;
    private int curMonthDay;
    private int curMonth;
    private int curWeek;
    private int showMonthOrWeek;
    private MessazyInfoWrap wrap;
    private List<MessazyInfo> infos;
    private double showBiggest;


    public DetailListAdapter(MessazyInfoWrap wrap, boolean isWeekShow, int showMonthOrWeek) {
        this.wrap = wrap;
        infos = wrap.infos;
        this.isWeekShow = isWeekShow;
        final Calendar cal = Calendar.getInstance();
        curMonth = cal.get(Calendar.MONTH) + 1;
        curWeek = cal.get(Calendar.WEEK_OF_MONTH);
        curMonthDay = cal.get(Calendar.DAY_OF_MONTH);
        curWeekDay = cal.get(Calendar.DAY_OF_WEEK)-1;
        if(curWeekDay == 0) {
            curWeekDay = 7;
        }
        this.showMonthOrWeek = showMonthOrWeek;
        if(!isWeekShow) {
            this.showMonthOrWeek++;
        }
        Log.d("rgf","curretn month day" + curMonthDay + " curweekday=" + curWeekDay + " curMonth=" + curMonth + "length=" + infos.size());
        showBiggest=0;
        for(MessazyInfo info:infos) {
            if(info.getMoney() > showBiggest) {
                showBiggest = info.money;
            }
        }
    }

    @Override
    public int getCount() {
        if(isWeekShow) {
            if(this.curWeek != this.showMonthOrWeek) {
                return 7;
            } else {
                Log.d("rgf","curWeekday" + curWeekDay);
                return curWeekDay;
            }
        } else {
            if(this.curMonth != this.showMonthOrWeek) {
                return 31;
            } else {
                Log.d("rgf","curMonthday" + curMonthDay);
                return curMonthDay;
            }
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_list_item, null);
            holder = new ViewHolder();
            holder.dateText = (TextView)convertView.findViewById(R.id.item_date);
            holder.detailProgress = (ProgressBarWithText)convertView.findViewById(R.id.item_progress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if(isWeekShow) {
            String mWeekday;
            switch (position) {
                case 0:
                    mWeekday = "周一";
                    break;
                case 1:
                    mWeekday = "周二";
                    break;
                case 2:
                    mWeekday = "周三";
                    break;
                case 3:
                    mWeekday = "周四";
                    break;
                case 4:
                    mWeekday = "周五";
                    break;
                case 5:
                    mWeekday = "周六";
                    break;
                case 6:
                    mWeekday = "周日";
                    break;
                default:
                    mWeekday = "";
            }
            holder.dateText.setText(mWeekday);
        } else {
            holder.dateText.setText(showMonthOrWeek + "." + (position + 1));
        }

        if(infos.size() != 0 && position < infos.size()) {
            MessazyInfo info = infos.get(position);
            holder.detailProgress.setProgress(((int)info.getMoney()) / (int)showBiggest * 100);
//            holder.detailProgress.setText((int)info.getMoney() + "");
            Log.e("rgf","getmoney=" + (int)info.getMoney());
        }
        return convertView;
    }

    static class ViewHolder {
        public TextView dateText;
        public ProgressBarWithText detailProgress;
    }

    public String TimeStamp2Date(String timestampString, String formats){
        Long timestamp = Long.parseLong(timestampString)*1000;
        String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));
        return date;
    }
}
