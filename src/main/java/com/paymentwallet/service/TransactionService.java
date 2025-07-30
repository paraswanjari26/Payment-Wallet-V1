package com.paymentwallet.service;

import com.paymentwallet.dto.ResponseDto;

public interface TransactionService {

    ResponseDto transferAmount(String transferFrom, String transferTo, double amount);
}
