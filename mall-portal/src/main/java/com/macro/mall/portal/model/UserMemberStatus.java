package com.macro.mall.portal.model;

public enum UserMemberStatus {
    DENY(0),
    OPEN(1),
    INIT(2);

    private int value;

    public int getValue() {
        return value;
    }

    UserMemberStatus(int i) {
        this.value = i;
    }
}
