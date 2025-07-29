package com.paymentwallet.service.impl;

import com.paymentwallet.constant.Constants;
import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.UniversalResponse;
import com.paymentwallet.dto.UserDto;
import com.paymentwallet.entity.Currency;
import com.paymentwallet.entity.User;
import com.paymentwallet.entity.Wallet;
import com.paymentwallet.repository.CurrencyRepository;
import com.paymentwallet.repository.UserRepository;
import com.paymentwallet.repository.WalletRepository;
import com.paymentwallet.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, Constants {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    @Override
    public ResponseDto add(UserDto userDto) {
        User user = userRepository.findByEmailId(userDto.getEmailId());
        if (user != null) {
            return new ResponseDto("User with given email id already exist", HttpStatus.CONFLICT);
        }
        user = new User();
        BeanUtils.copyProperties(userDto, user, "userId");
        user.setUserId(String.valueOf(UUID.randomUUID()));
        userRepository.save(user);

        List<Currency> currency = currencyRepository.findAll();

        Wallet wallet = new Wallet();
        wallet.setWalletId(String.valueOf(UUID.randomUUID()));
        wallet.setUser(user);
        wallet.setCurrency(currency.getFirst());
        wallet.setBalance(0.0);
        walletRepository.save(wallet);

        return new ResponseDto("User Registered Successfully", HttpStatus.OK);
    }

    @Override
    public UniversalResponse<User> retrieve(String userId) {
        UniversalResponse<User> ur = new UniversalResponse<>();
        User user = userRepository.findByUserIdAndDeleted(userId, NON_DELETED);
        if (user == null) {
            ur.setResponseDto(new ResponseDto("No such user found", HttpStatus.CONFLICT));
            return ur;
        }
        ur.setObject(user);
        ur.setResponseDto(new ResponseDto("Success", HttpStatus.OK));
        return ur;
    }

    @Override
    public UniversalResponse<List<User>> retrieveAll() {
        UniversalResponse<List<User>> ur = new UniversalResponse<>();
        List<User> userList = userRepository.findAllByDeleted(NON_DELETED);
        if (userList.isEmpty()) {
            ur.setResponseDto(new ResponseDto("No users found", HttpStatus.CONFLICT));
            return ur;
        }
        ur.setList(userList);
        ur.setResponseDto(new ResponseDto("Success", HttpStatus.OK));
        return ur;
    }

    @Override
    public ResponseDto remove(String userId) {
        User user = userRepository.findByUserIdAndDeleted(userId, NON_DELETED);
        if (user != null) {
            user.setDeleted(DELETED);
            userRepository.save(user);
            return new ResponseDto("User Registered Successfully", HttpStatus.OK);
        }
        return new ResponseDto("User Registered Successfully", HttpStatus.OK);
    }
}
