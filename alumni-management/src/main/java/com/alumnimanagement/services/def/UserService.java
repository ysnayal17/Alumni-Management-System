package com.alumnimanagement.services.def;

import com.alumnimanagement.entity.User;
import com.alumnimanagement.web.dto.LoginRequestDTO;
import com.alumnimanagement.web.dto.LoginResponseDTO;
import com.alumnimanagement.web.dto.UserDTO;

import java.io.IOException;

public interface UserService {

    void createUser(UserDTO userDTO);

    LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) throws IOException;

    User getUserDetails(String userId);
}
