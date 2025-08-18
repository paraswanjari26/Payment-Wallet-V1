package com.paymentwallet.controller;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.UniversalResponse;
import com.paymentwallet.entity.Wallet;
import com.paymentwallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/users/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @PatchMapping(value = "/addAmount")
    public ResponseEntity<ResponseDto> addAmount(@RequestParam String emailId, @RequestParam double amount) {
        ResponseDto responseDto = walletService.addAmount(emailId, amount);

        EntityModel<ResponseDto> model = EntityModel.of(responseDto);
        model.add(linkTo(methodOn(WalletController.class).addAmount(emailId, amount)).withSelfRel());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/checkBalance")
    public ResponseEntity<?> checkBalance(@RequestParam String emailId) {
        UniversalResponse<Wallet> ur = walletService.checkBalance(emailId);
        if (ur.getObject() != null) {
            Wallet wallet = ur.getObject();

            EntityModel<Wallet> model = EntityModel.of(wallet);
            model.add(linkTo(methodOn(WalletController.class).checkBalance(emailId)).withSelfRel());
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        return new ResponseEntity<>(ur.getResponseDto(), HttpStatus.OK);
    }
}
