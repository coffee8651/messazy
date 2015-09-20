package com.coffee.messzay.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.text.format.DateUtils;
import android.util.Log;

import com.coffee.messzay.vo.MessazyInfo;
import com.coffee.messzay.vo.MessazyInfoWrap;


public class MessazyDataMgr {

    private CopyOnWriteArrayList<MessazyInfo> mMessazyInfos = new CopyOnWriteArrayList<MessazyInfo>();

    private static MessazyDataMgr sInstance;
    private Calendar calendar = Calendar.getInstance();

    public synchronized static MessazyDataMgr getInstance() {
        if (sInstance == null) {
            sInstance = new MessazyDataMgr();
        }
        return sInstance;
    }

    private MessazyDataMgr() {
    }

    public void addMessazy(MessazyInfo info) {
        mMessazyInfos.add(info);

        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.notifyChanged();
        }
    }

    public void addAllMessazy(List<MessazyInfo> list) {
        for (int i = 30; i > 0; i--) {
            for (MessazyInfo info : list) {
                MessazyInfo temp = new MessazyInfo();
                temp.card = info.card;
                temp.chargeType = info.chargeType;
                temp.money = info.money;
                temp.type = info.type;
                temp.time = info.time - i * DateUtils.WEEK_IN_MILLIS;
                mMessazyInfos.add(temp);
            }
        }

        mMessazyInfos.addAll(list);

        for (int i = 0; i < 13; i++) {
            for (MessazyInfo info : list) {
                MessazyInfo temp = new MessazyInfo();
                temp.card = info.card;
                temp.chargeType = info.chargeType;
                temp.money = info.money;
                temp.type = info.type;
                temp.time = info.time + i * DateUtils.WEEK_IN_MILLIS;
                mMessazyInfos.add(temp);
            }
        }

        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.notifyChanged();
        }
    }

    public MessazyInfoWrap getExpenditureByWeek(int month, int week) {
        double expenditure = 0;
        double expenditureOfATM = 0;
        MessazyInfoWrap wrap = new MessazyInfoWrap();
        List<MessazyInfo> temp = new ArrayList<MessazyInfo>();
        for (MessazyInfo info : mMessazyInfos) {
            calendar.setTimeInMillis(info.time);
            int month1 = calendar.get(Calendar.MONTH);
            int week1 = calendar.get(Calendar.WEEK_OF_MONTH);

            Log.e("00000", "month=" + month1);
            Log.e("00000", "week=" + week1);

            if (month == month1 && week == week1) {
                temp.add(info);
                expenditure += info.money;

                //atm
                if (info.type == 1) {
                    expenditureOfATM += info.money;
                }
            }
        }
        wrap.infos = temp;
        wrap.expenditureOfATM = expenditureOfATM;
        wrap.totalExpenditure = expenditure;
        return wrap;
    }

    public MessazyInfoWrap getExpenditureByMonth(int month) {
        double expenditure = 0;
        double expenditureOfATM = 0;
        MessazyInfoWrap wrap = new MessazyInfoWrap();
        List<MessazyInfo> temp = new ArrayList<MessazyInfo>();
        for (MessazyInfo info : mMessazyInfos) {
            calendar.setTimeInMillis(info.time);
            int month1 = calendar.get(Calendar.MONTH);

            Log.e("00000", "month=" + month1);

            if (month == month1) {
                temp.add(info);
                expenditure += info.money;

                //atm
                if (info.type == 1) {
                    expenditureOfATM += info.money;
                }
            }
        }
        wrap.infos = temp;
        wrap.expenditureOfATM = expenditureOfATM;
        wrap.totalExpenditure = expenditure;
        return wrap;
    }

    public interface OnDataChangedListener {
        void notifyChanged();
    }

    private OnDataChangedListener mOnDataChangedListener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }
}
