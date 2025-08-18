package com.paymentwallet.service;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.TransferRequestDto;

public interface TransactionService {

    ResponseDto transferAmount(TransferRequestDto transferRequestDto);
}
