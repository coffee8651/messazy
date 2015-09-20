package com.coffee.messzay.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;

import com.coffee.messzay.data.MessazyDataMgr;
import com.coffee.messzay.prase.PraseHelper;
import com.coffee.messzay.vo.MessazyInfo;

public class MessageReceiver extends BroadcastReceiver {

    private static final String GSM_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (!TextUtils.isEmpty(action) && GSM_SMS_RECEIVED.equals(action)) {
            handleSms(intent);
        }
    }

    private void handleSms(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            MessazyDataMgr dataMgr = MessazyDataMgr.getInstance();
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                byte[] pdu = (byte[]) pdus[i];
                messages[i] = SmsMessage.createFromPdu(pdu);
            }

            for (SmsMessage msg : messages) {
                MessazyInfo info = PraseHelper.prase(msg);
                if (info != null) {
                    dataMgr.addMessazy(info);
                }
            }
        }
    }

}
