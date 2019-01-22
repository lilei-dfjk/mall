package com.macro.mall.exception;

import com.macro.mall.logistcs.service.ExpressCalcService;

public class LogisticsException extends Exception {

    public LogisticsException(String message, Throwable cause) {
        super(message, cause);
    }
}
