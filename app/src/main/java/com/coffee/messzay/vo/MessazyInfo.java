package com.coffee.messzay.vo;

public class MessazyInfo {

    /** 卡号 */
    public String card;
    /** 消费时间 */
    public long time;
    /** 支出 0, 收入1*/
    public int chargeType;
    /** 金额 */
    public double money;
    /** 正常消费0, 银行卡支出1*/
    public int type;
    
    @Override
    public String toString() {
        return "MessazyInfo [card=" + card + ", time=" + time + ", chargeType=" + chargeType + ", money=" + money
                + ", type=" + type + "]";
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getChargeType() {
        return chargeType;
    }

    public void setChargeType(int chargeType) {
        this.chargeType = chargeType;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
}
