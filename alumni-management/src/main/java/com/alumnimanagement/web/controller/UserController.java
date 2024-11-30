package com.alumnimanagement.web.controller;


import com.alumnimanagement.entity.User;
import com.alumnimanagement.services.def.UserService;
import com.alumnimanagement.web.dto.APIResponseDTO;
import com.alumnimanagement.web.dto.LoginRequestDTO;
import com.alumnimanagement.web.dto.LoginResponseDTO;
import com.alumnimanagement.web.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<APIResponseDTO<String>> createUser(@Valid @RequestBody UserDTO userDTO){
        this.userService.createUser(userDTO);
        return new ResponseEntity<>(APIResponseDTO.<String>builder()
                .payload("User created successfully")
                .build(), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponseDTO<LoginResponseDTO>> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) throws IOException {

        return new ResponseEntity<>(APIResponseDTO.<LoginResponseDTO>builder()
                .payload(this.userService.loginUser(loginRequestDTO))
                .build(),HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponseDTO<User>> getUserDetails(@RequestHeader("userId")String userId){

        return new ResponseEntity<>(APIResponseDTO.<User>builder()
                .payload(this.userService.getUserDetails(userId))
                .build(),HttpStatus.OK);
    }
}
