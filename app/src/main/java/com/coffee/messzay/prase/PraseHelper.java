package com.coffee.messzay.prase;

import android.telephony.SmsMessage;
import android.util.Log;

import com.coffee.messzay.vo.MessazyInfo;


public class PraseHelper {

    private static final String BANK_NUMBER = "7060";

    private static final String CARD_KEY = "尾号";
    private static final String ATM = "支出(ATM取款)";
    private static final String YUAN = "元";

    private static final String CONSUME[] = { "支出(消费)", "(消费支出)", "(预授权确认)" };

    public static MessazyInfo prase(SmsMessage msg) {
        return prase(msg.getOriginatingAddress(), msg.getMessageBody(), msg.getTimestampMillis());
    }

    public static MessazyInfo prase(String sender, String content, long time) {
        if (sender.contains(BANK_NUMBER)) {
            String tempKey = ATM;
            int start = content.indexOf(ATM);
            if (start == -1) {
                for (String key : CONSUME) {
                    tempKey = key;
                    start = content.indexOf(key);
                    if (start != -1) {
                        break;
                    }
                }
            }

            if (start == -1) {
                return null;
            }

            MessazyInfo info = new MessazyInfo();
            int cardStart = content.indexOf(CARD_KEY);
            info.card = content.substring(cardStart + CARD_KEY.length(), cardStart + CARD_KEY.length() + 4);
            int end = content.indexOf(YUAN);
            info.money = Double.parseDouble(content.substring(start + tempKey.length(), end));
            info.time = time;
            if (tempKey.equals(ATM)) {
                info.type = 1;
            }
            
            Log.e("00000", info.toString());
            return info;
        }

        return null;
    }
}
