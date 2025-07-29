package com.paymentwallet.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDto {
    private String message;
    private HttpStatus code;

    public ResponseDto(String s, HttpStatus httpStatus) {
    }
}
