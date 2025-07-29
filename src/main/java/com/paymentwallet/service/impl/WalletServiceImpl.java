package com.paymentwallet.service.impl;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.UniversalResponse;
import com.paymentwallet.entity.User;
import com.paymentwallet.repository.WalletRepository;
import com.paymentwallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Override
    public ResponseDto addAmount(String emailId, double amount) {
        return null;
    }

    @Override
    public UniversalResponse<User> checkBalance(String emailId) {
        return null;
    }
}
