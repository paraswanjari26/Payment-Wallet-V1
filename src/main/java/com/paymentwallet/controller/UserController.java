package com.paymentwallet.controller;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.UniversalResponse;
import com.paymentwallet.dto.UserDto;
import com.paymentwallet.entity.User;
import com.paymentwallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody UserDto userDto) {
        ResponseDto responseDto = userService.add(userDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/retrieve")
    public ResponseEntity<?> retrieve(@RequestParam String userId) {
        UniversalResponse<User> ur = userService.retrieve(userId);
        return new ResponseEntity<>(ur, HttpStatus.OK);
    }

    @GetMapping(value = "/retrieveAll")
    public ResponseEntity<?> retrieveAll() {
        UniversalResponse<List<User>> ur = userService.retrieveAll();
        return new ResponseEntity<>(ur, HttpStatus.OK);
    }

    @PatchMapping(value = "/remove")
    public ResponseEntity<?> remove(@RequestParam String userId) {
        ResponseDto responseDto = userService.remove(userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
