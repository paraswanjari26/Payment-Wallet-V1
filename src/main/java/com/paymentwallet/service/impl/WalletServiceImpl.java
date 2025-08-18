package com.paymentwallet.service.impl;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.UniversalResponse;
import com.paymentwallet.entity.User;
import com.paymentwallet.entity.Wallet;
import com.paymentwallet.repository.UserRepository;
import com.paymentwallet.repository.WalletRepository;
import com.paymentwallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    WalletRepository walletRepository;

    @Override
    public ResponseDto addAmount(String emailId, double amount) {
        User user = userRepository.findByEmailId(emailId);
        if (user != null) {
            Wallet wallet = walletRepository.findByUser(user);
            if (wallet != null) {
                wallet.setBalance(amount);
                walletRepository.save(wallet);
                return new ResponseDto("Amount added successfully", HttpStatus.OK);
            }
        }
        return new ResponseDto("There is no user registered with above emailId", HttpStatus.NOT_FOUND);
    }

    @Override
    public UniversalResponse<Wallet> checkBalance(String emailId) {
        UniversalResponse<Wallet> ur = new UniversalResponse<>();
        User user = userRepository.findByEmailId(emailId);
        if (user != null) {
            Wallet wallet = walletRepository.findByUser(user);
            if (wallet != null) {
                ur.setObject(wallet);
                ur.setResponseDto(new ResponseDto("Success", HttpStatus.OK));
                return ur;
            }
        }
        ur.setResponseDto(new ResponseDto("There is no user registered with above emailId", HttpStatus.NOT_FOUND));
        return ur;
    }
}
