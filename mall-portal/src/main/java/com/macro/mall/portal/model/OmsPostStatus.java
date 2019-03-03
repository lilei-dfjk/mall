package com.macro.mall.portal.model;

/**
 * 1,初始化;2,处理中;3,已处理
 */
public enum OmsPostStatus {
    COMPLETE(3),
    HANDLING(2),
    INIT(1);
    private int value;

    OmsPostStatus(int i) {
        this.value = i;
    }

    public static String getNameByValue(int value) {
        OmsPostStatus[] values = OmsPostStatus.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].value == value) {
                return values[i].name();
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
