package com.macro.mall.logistcs.cons;

public enum LogisticType {
    ZH(1),
    AD(2);
    private int value;
    private LogisticType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
