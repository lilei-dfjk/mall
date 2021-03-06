package com.macro.mall.portal.model;

/**
 * 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单;6->已删除
 */
public enum OmsPayStatus {
    DELETE(6),
    UNVALIAD(5),
    CLOSED(4),
    COMPLETE(3),
    POSTED(2),
    UNPOSTED(1),
    UNDERPAY(0);
    private int value;

    OmsPayStatus(int i) {
        this.value = i;
    }

    public static String getNameByValue(int value) {
        OmsPayStatus[] values = OmsPayStatus.values();
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
