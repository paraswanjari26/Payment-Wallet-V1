package com.paymentwallet.controller;

import com.paymentwallet.dto.ResponseDto;
import com.paymentwallet.dto.UniversalResponse;
import com.paymentwallet.dto.UserDto;
import com.paymentwallet.entity.User;
import com.paymentwallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody UserDto userDto) {
        ResponseDto responseDto = userService.add(userDto);

        EntityModel<ResponseDto> model = EntityModel.of(responseDto);
        model.add(linkTo(methodOn(UserController.class).add(userDto)).withSelfRel());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/retrieve")
    public ResponseEntity<?> retrieve(@RequestParam String userId) {
        UniversalResponse<User> ur = userService.retrieve(userId);
        if (ur.getObject() != null) {
            User user = ur.getObject();

            EntityModel<User> model = EntityModel.of(user);
            model.add(linkTo(methodOn(UserController.class).retrieve(userId)).withSelfRel());
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        return new ResponseEntity<>(ur.getResponseDto(), HttpStatus.OK);
    }

    @GetMapping(value = "/retrieveAll")
    public ResponseEntity<?> retrieveAll() {
        UniversalResponse<List<User>> ur = userService.retrieveAll();

        if (ur.getObject() != null && !ur.getList().isEmpty()) {
            List<EntityModel<User>> userModels = ur.getObject().stream()
                    .map(user -> EntityModel.of(user,
                            linkTo(methodOn(UserController.class).retrieveAll()).withSelfRel()
                    ))
                    .toList();

            CollectionModel<EntityModel<User>> collectionModel = CollectionModel.of(userModels);
            collectionModel.add(linkTo(methodOn(UserController.class).retrieveAll()).withSelfRel());

            return new ResponseEntity<>(collectionModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(ur, HttpStatus.OK);
    }

    @PatchMapping(value = "/remove")
    public ResponseEntity<?> remove(@RequestParam String userId) {
        ResponseDto responseDto = userService.remove(userId);

        EntityModel<ResponseDto> model = EntityModel.of(responseDto);
        model.add(linkTo(methodOn(UserController.class).remove(userId)).withSelfRel());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
