package com.alumnimanagement.web.controller;


import com.alumnimanagement.exception.DetailsNotFoundException;
import com.alumnimanagement.services.def.UserService;
import com.alumnimanagement.web.dto.APIResponseDTO;
import com.alumnimanagement.web.dto.LoginRequestDTO;
import com.alumnimanagement.web.dto.LoginResponseDTO;
import com.alumnimanagement.web.dto.UserDTO;
import com.alumnimanagement.web.dto.UserDetailsFromFileDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add-file",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponseDTO<String>> addUsersFromSheet(@Valid UserDetailsFromFileDTO userDetailsFromFileDTO){

        this.userService.addUsersFromFile(userDetailsFromFileDTO.getFile());
        return new ResponseEntity<>(APIResponseDTO.<String>builder()
                .payload("User(s) created successfully")
                .build(), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<APIResponseDTO<String>> createUser(@Valid @RequestBody UserDTO userDTO){
        this.userService.createUser(userDTO);
        return new ResponseEntity<>(APIResponseDTO.<String>builder()
                .payload("User created successfully")
                .build(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponseDTO<String>> updateUserDetails(@Valid UserDTO userDTO) throws IOException {
        this.userService.updateUserDetails(userDTO);
        return new ResponseEntity<>(APIResponseDTO.<String>builder()
                .payload("User Updated successfully")
                .build(), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponseDTO<LoginResponseDTO>> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) throws IOException {

        return new ResponseEntity<>(APIResponseDTO.<LoginResponseDTO>builder()
                .payload(this.userService.loginUser(loginRequestDTO))
                .build(),HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ALUMNI','STUDENT')")
    public ResponseEntity<APIResponseDTO<UserDTO>> getUserDetails(@RequestHeader("userId")String userId){

        return new ResponseEntity<>(APIResponseDTO.<UserDTO>builder()
                .payload(this.userService.getUserDetails(userId))
                .build(),HttpStatus.OK);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN','ALUMNI','STUDENT')")
    public ResponseEntity<APIResponseDTO<List<UserDTO>>> getUserList(){

        return new ResponseEntity<>(APIResponseDTO.<List<UserDTO>>builder()
                .payload(this.userService.getUserList())
                .build(),HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<APIResponseDTO<String>> logout(@RequestHeader("RefreshToken") String refreshToken, @RequestHeader("userId") String userId) throws IOException, DetailsNotFoundException {

        boolean isLoggedOut = userService.logout(refreshToken, userId);

        if (isLoggedOut) {
            return new ResponseEntity<>(APIResponseDTO.<String>builder()
                    .payload("Logged out successfully")
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(APIResponseDTO.<String>builder()
                .payload("Something went wrong")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
