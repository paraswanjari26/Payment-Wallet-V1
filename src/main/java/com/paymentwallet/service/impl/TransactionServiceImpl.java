package com.paymentwallet.service.impl;

import com.paymentwallet.constant.Constants;
import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.TransferRequestDto;
import com.paymentwallet.entity.Transaction;
import com.paymentwallet.entity.User;
import com.paymentwallet.entity.Wallet;
import com.paymentwallet.repository.TransactionRepository;
import com.paymentwallet.repository.UserRepository;
import com.paymentwallet.repository.WalletRepository;
import com.paymentwallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService, Constants {

    @Autowired
    UserRepository userRepository;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public ResponseDto transferAmount(TransferRequestDto transferRequestDto) {
        try {
            User fromUser = userRepository.findByEmailIdAndDeleted(transferRequestDto.getTransferFrom(), NON_DELETED);
            if (fromUser == null) {
                return new ResponseDto("Sender account not found", HttpStatus.NOT_FOUND);
            }

            User toUser = userRepository.findByEmailIdAndDeleted(transferRequestDto.getTransferTo(), NON_DELETED);
            if (toUser == null) {
                return new ResponseDto("Receiver account not found", HttpStatus.NOT_FOUND);
            }

            return transferAmount(fromUser, toUser, transferRequestDto.getAmount());

        } catch (IllegalArgumentException e) {
            return new ResponseDto(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseDto("An unexpected error occurred", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseDto transferAmount(User fromUser, User toUser, double amount) {
        Wallet from = walletRepository.findByUser(fromUser);
        if (from == null) {
            return new ResponseDto("Sender wallet not found.", HttpStatus.NOT_FOUND);
        }

        try {
            if (!checkBalanceValidation(from, amount)) {
                throw new IllegalArgumentException("Invalid transfer amount.");
            }

            Wallet to = walletRepository.findByUser(toUser);
            if (to == null) {
                return new ResponseDto("Receiver wallet not found.", HttpStatus.NOT_FOUND);
            }

            // Perform the transfer
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);

            // Save updated wallets
            walletRepository.save(from);
            walletRepository.save(to);

            Transaction transaction = new Transaction();
            transaction.setTransferId(String.valueOf(UUID.randomUUID()));
            transaction.setAmount(amount);
            transaction.setDate(LocalDate.now());
            transaction.setTime(LocalTime.now());
            transaction.setFromEmail(fromUser.getEmailId());
            transaction.setToEmail(toUser.getEmailId());
            transaction.setStatus("Success");
            transactionRepository.save(transaction);

            return new ResponseDto("Transfer successful", HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseDto(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    public static boolean checkBalanceValidation(Wallet from, double amount) {
        if (from == null) {
            throw new IllegalArgumentException("Source wallet cannot be null.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
        if (from.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance in the source wallet.");
        }
        return true;
    }
}
