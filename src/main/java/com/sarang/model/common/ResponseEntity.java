package com.sarang.model.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEntity {
    private boolean succeed;
    private String message;
    private Object data;
}
