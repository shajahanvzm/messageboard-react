package com.sha.mb.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@Getter
public class MessageAPIException  extends  RuntimeException{
    private HttpStatus status;
    private String message;
}
