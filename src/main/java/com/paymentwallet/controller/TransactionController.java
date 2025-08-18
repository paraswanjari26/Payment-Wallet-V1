package com.paymentwallet.controller;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.TransferRequestDto;
import com.paymentwallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    @PostMapping(value = "/transferAmount")
    public ResponseEntity<?> transferAmount(@RequestBody TransferRequestDto transferRequestDto) {
        ResponseDto responseDto = transactionService.transferAmount(transferRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
