package com.paymentwallet.service;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.UniversalResponse;
import com.paymentwallet.entity.User;

public interface WalletService {
    ResponseDto addAmount(String emailId, double amount);

    UniversalResponse<User> checkBalance(String emailId);
}
