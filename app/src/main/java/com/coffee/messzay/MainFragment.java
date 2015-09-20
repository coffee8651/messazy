package com.coffee.messzay;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.coffee.messzay.adapter.DetailListAdapter;
import com.coffee.messzay.data.DBMessageLoader;
import com.coffee.messzay.data.MessazyDataMgr;
import com.coffee.messzay.vo.MessazyInfo;
import com.coffee.messzay.vo.MessazyInfoWrap;

import java.util.Calendar;
import java.util.List;

public class MainFragment extends Fragment {
    private View rootView;
    private int showMonOrWeek;
    private boolean isWeekShow;
    private int CurMonth;
    private ListView detailList;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Calendar cal = Calendar.getInstance();
        CurMonth = cal.get(Calendar.MONTH) + 1;
        showMonOrWeek = getArguments().getInt(Comment.curMonthOrWeek);
        isWeekShow = getArguments().getBoolean(Comment.isWeekShow);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
        }
        init(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private void init(View view) {
        view.findViewById(R.id.header_calender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWeekShow) {
                    ((MainActivity)getActivity()).initMonth();
                } else {
                    ((MainActivity)getActivity()).initWeek();
                }
            }
        });

        view.findViewById(R.id.pie_chart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).startNewActivity();
            }
        });

        if(isWeekShow) {
            ((TextView) view.findViewById(R.id.total_title)).setText(CurMonth + "月第" + showMonOrWeek + "周");
        } else {
            ((TextView)view.findViewById(R.id.total_title)).setText(showMonOrWeek + "月");
        }

        TextView totalNum = (TextView)view.findViewById(R.id.total_num);

        detailList = (ListView)view.findViewById(R.id.detail_list);


        MessazyInfoWrap wrap;
        if (isWeekShow) {
            wrap = MessazyDataMgr.getInstance().getExpenditureByWeek(CurMonth, showMonOrWeek);
        } else {
            wrap = MessazyDataMgr.getInstance().getExpenditureByMonth(showMonOrWeek);
        }
        totalNum.setText("￥" + (int)wrap.totalExpenditure);
        if (wrap != null) {
            Log.e("00000", "expenditureOfATM=" + wrap.expenditureOfATM);
            Log.e("00000", "totalExpenditure=" + wrap.totalExpenditure);
            List<MessazyInfo> infos = wrap.infos;

            for (MessazyInfo info : infos) {
                Log.e("00000", info.toString());
            }
        }

        final DetailListAdapter adapter;
        if (isWeekShow) {
            adapter = new DetailListAdapter( wrap, isWeekShow, showMonOrWeek);
        } else {
            adapter = new DetailListAdapter( wrap, isWeekShow, showMonOrWeek - 1);
        }
        detailList.setAdapter(adapter);



        MessazyDataMgr.getInstance().setOnDataChangedListener(new MessazyDataMgr.OnDataChangedListener() {
            @Override
            public void notifyChanged() {

                adapter.notifyDataSetChanged();
            }
        });
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
        }
    };



}
