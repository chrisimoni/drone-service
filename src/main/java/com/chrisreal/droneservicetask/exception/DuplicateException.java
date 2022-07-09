package com.chrisreal.droneservicetask.exception;

public class DuplicateException extends BaseException {
    public static final String CODE= "09";
    public DuplicateException(String message) {
        super(CODE, message);
    }
}
