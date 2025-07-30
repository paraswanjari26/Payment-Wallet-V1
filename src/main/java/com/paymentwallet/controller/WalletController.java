package com.paymentwallet.controller;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.UniversalResponse;
import com.paymentwallet.dto.UserDto;
import com.paymentwallet.entity.User;
import com.paymentwallet.entity.Wallet;
import com.paymentwallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @PostMapping(value = "/addAmount")
    public ResponseEntity<?> addAmount(@RequestParam String emailId, @RequestParam double amount) {
        ResponseDto responseDto = walletService.addAmount(emailId, amount);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/checkBalance")
    public ResponseEntity<?> checkBalance(@RequestParam String emailId) {
        UniversalResponse<Wallet> ur = walletService.checkBalance(emailId);
        return new ResponseEntity<>(ur, HttpStatus.OK);
    }
}
