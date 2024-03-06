package com.sarang.model.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseData {
    private boolean isSucceed;
    private String message;
    private Object data;

    public boolean getIsSucceed(){
        return this.isSucceed;
    }
    public void setIsSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }
}
