package com.codeittoday.dev_checkpoint.dto;

public class TestingStatus {

    private SubStatus success;
    private SubStatus fault;
    private SubStatus exception;

    public SubStatus getSuccess() { return success; }
    public void setSuccess(SubStatus success) { this.success = success; }

    public SubStatus getFault() { return fault; }
    public void setFault(SubStatus fault) { this.fault = fault; }

    public SubStatus getException() { return exception; }
    public void setException(SubStatus exception) { this.exception = exception; }
}
