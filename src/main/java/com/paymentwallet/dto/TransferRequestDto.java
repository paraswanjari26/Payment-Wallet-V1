package com.paymentwallet.dto;

import lombok.Data;

@Data
public class TransferRequestDto {
    private String transferFrom;
    private String transferTo;
    private double amount;
}
