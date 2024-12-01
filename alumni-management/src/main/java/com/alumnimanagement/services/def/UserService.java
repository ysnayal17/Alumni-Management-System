package com.alumnimanagement.services.def;

import com.alumnimanagement.web.dto.LoginRequestDTO;
import com.alumnimanagement.web.dto.LoginResponseDTO;
import com.alumnimanagement.web.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    void createUser(UserDTO userDTO);

    LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) throws IOException;

    UserDTO getUserDetails(String userId);

    List<UserDTO> getUserList();

    boolean logout(String refreshToken, String userId) throws IOException;

    void addUsersFromFile(MultipartFile file);

    void updateUserDetails(UserDTO userDTO) throws IOException;
}
