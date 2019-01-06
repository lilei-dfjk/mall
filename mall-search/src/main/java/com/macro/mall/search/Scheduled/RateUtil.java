package com.macro.mall.search.Scheduled;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RateUtil {

    @Value("memberId")
    private String memberId;
    @Value("memberKey")
    private String memberKey;


}
