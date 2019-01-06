package com.macro.mall.search.Scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RateScheduled {

    @Scheduled(cron = "0 05 10 * * ?")
    public void updateRate() {

    }

}
