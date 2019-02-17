package com.macro.mall.portal.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserMemberModel implements Serializable {
    private Long memberId;
    private String username;
    private String telephone;
    private String nickname;
    private String headPic;
    private String level;
}
