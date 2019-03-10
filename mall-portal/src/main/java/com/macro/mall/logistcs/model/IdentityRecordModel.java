package com.macro.mall.logistcs.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class IdentityRecordModel implements Serializable {
    private String name;
    private String identityNo;
    private String identityFrontUrl;
    private String identityBackUrl;
}
