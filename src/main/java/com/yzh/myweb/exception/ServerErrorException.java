package com.yzh.myweb.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 服务端产生的异常
 */
public class ServerErrorException extends CustomException {

	private String errorCode;
	
    public ServerErrorException(String message) {
        super(message);
    }
    
    public ServerErrorException(String errorCode, String message) {
        super(message);

        this.errorCode = errorCode;
    }

    @Override
    public String getErrorCode() {
    	if(StringUtils.isEmpty(errorCode)){
    		return "500";
    	}
    	return errorCode;
    }

}
