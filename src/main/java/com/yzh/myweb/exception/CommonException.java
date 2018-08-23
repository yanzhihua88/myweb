package com.yzh.myweb.exception;


public class CommonException extends CustomException{

	private static final long serialVersionUID = 1L;

	public CommonException(String message, Throwable t) {
        super(message, t);
    }

    public CommonException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "SY502";
    }
}
