package com.chrisreal.droneservicetask.exception;

public class NotFoundException extends BaseException {
    public static final String CODE= "04";
    public NotFoundException(String message) {
        super(CODE, message);
    }
}
