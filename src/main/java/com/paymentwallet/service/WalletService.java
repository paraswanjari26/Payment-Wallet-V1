package com.paymentwallet.service;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.UniversalResponse;
import com.paymentwallet.entity.User;
import com.paymentwallet.entity.Wallet;

public interface WalletService {
    ResponseDto addAmount(String emailId, double amount);

    UniversalResponse<Wallet> checkBalance(String emailId);
}
