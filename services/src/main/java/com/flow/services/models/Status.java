package com.flow.services.models;

public enum Status {
    NOT_STARTED(0),
    RUNNING(1),
    SUCCESS(2),
    FAILED(3),
    KILLED(4);

    private Integer code;

    Status(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
