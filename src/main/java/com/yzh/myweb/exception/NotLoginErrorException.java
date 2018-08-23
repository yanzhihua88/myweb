package com.yzh.myweb.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: seven
 * @create: 2018-07-19 13:51
 */
public class NotLoginErrorException extends CustomException {
    public NotLoginErrorException() {
        super("未登录或者登录已失效");
    }

    @Override
    public String getErrorCode() {
        return "505";
    }
}
