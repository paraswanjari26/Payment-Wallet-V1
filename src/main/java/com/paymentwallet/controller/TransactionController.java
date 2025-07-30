package com.paymentwallet.controller;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "/transferAmount")
    public ResponseEntity<?> transferAmount(@RequestParam String transferFrom, @RequestParam String transferTo, @RequestParam double amount) {
        ResponseDto responseDto = transactionService.transferAmount(transferFrom, transferTo, amount);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
