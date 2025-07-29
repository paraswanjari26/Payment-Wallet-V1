package com.paymentwallet.service;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.UniversalResponse;
import com.paymentwallet.dto.UserDto;
import com.paymentwallet.entity.User;

import java.util.List;

public interface UserService {

    ResponseDto add(UserDto userDto);

    UniversalResponse<User> retrieve(String userId);

    UniversalResponse<List<User>> retrieveAll();

    ResponseDto remove(String userId);
}
