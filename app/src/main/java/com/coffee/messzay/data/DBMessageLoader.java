package com.coffee.messzay.data;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.coffee.messzay.prase.PraseHelper;
import com.coffee.messzay.vo.MessazyInfo;

public class DBMessageLoader {

    private Handler handler = new Handler();
    private static DBMessageLoader sInstance;

    private boolean loadedDBSms;

    public synchronized static DBMessageLoader getInstance() {
        if (sInstance == null) {
            sInstance = new DBMessageLoader();
        }
        return sInstance;
    }

    private DBMessageLoader() {
    }

    public interface OnLoadCallback {
        void onLoadFinish();
    }

    private static final String SMS_URI_INBOX = "content://sms/inbox";

    public void load(final Context c, final OnLoadCallback callback) {
        if (loadedDBSms) {
            return;
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri uri = Uri.parse(SMS_URI_INBOX);
                String[] projection = new String[] { "_id", "address", "body", "date" };
                Cursor cursor = c.getContentResolver().query(uri, projection, null, null, "date desc");
                if (cursor != null) {
                    final List<MessazyInfo> list = new ArrayList<MessazyInfo>();
                    while (cursor.moveToNext()) {
                        int indexAddress = cursor.getColumnIndex("address");
                        int indexBody = cursor.getColumnIndex("body");
                        int indexDate = cursor.getColumnIndex("date");

                        String strAddress = cursor.getString(indexAddress);
                        String strbody = cursor.getString(indexBody);
                        long longDate = cursor.getLong(indexDate);
                        MessazyInfo info = PraseHelper.prase(strAddress, strbody, longDate);
                        if (info != null) {
                            list.add(info);
                        }
                    }

                    MessazyInfo info = new MessazyInfo();
                    info.card = "1234";
                    info.money = Math.random() * 1000;
                    info.time = System.currentTimeMillis();
                    info.type = info.money > 500 ? 1 : 0;
                    list.add(info);

                    if (list != null && list.size() > 0) {
                        MessazyDataMgr.getInstance().addAllMessazy(list);
                    }
                    if (callback != null) {
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                callback.onLoadFinish();
                            }
                        });
                    }
                }
            }
        }).start();

        loadedDBSms = true;
    }
}
