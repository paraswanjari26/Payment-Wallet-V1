package com.paymentwallet.dto;

import lombok.Data;

import java.util.List;

@Data
public class UniversalResponse<T> {
    private ResponseDto responseDto;
    private List<?> list;
    private T object;
}
