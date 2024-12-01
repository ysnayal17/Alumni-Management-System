package com.alumnimanagement.services.impl;

import com.alumnimanagement.config.KeycloakConfigurationProperties;
import com.alumnimanagement.entity.Address;
import com.alumnimanagement.entity.User;
import com.alumnimanagement.enums.Department;
import com.alumnimanagement.enums.Role;
import com.alumnimanagement.exception.DetailsNotFoundException;
import com.alumnimanagement.exception.ExternalServiceException;
import com.alumnimanagement.exception.IncorrectCredentialsException;
import com.alumnimanagement.exception.UserNotFoundException;
import com.alumnimanagement.repo.UserRepository;
import com.alumnimanagement.services.def.KeycloakLoginService;
import com.alumnimanagement.services.def.UserService;
import com.alumnimanagement.web.dto.KeycloakUserDTO;
import com.alumnimanagement.web.dto.LoginRequestDTO;
import com.alumnimanagement.web.dto.LoginResponseDTO;
import com.alumnimanagement.web.dto.UserDTO;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;
    private final UserRepository userRepository;
    private final String realm;
    private final String defaultRole;
    private final String baseURL;
    private final String clientId;
    private final String clientSecret;

    public UserServiceImpl(Keycloak keycloak, UserRepository userRepository, KeycloakConfigurationProperties keycloakConfigurationProperties) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
        this.realm = keycloakConfigurationProperties.getApplicationRealm();
        this.defaultRole = keycloakConfigurationProperties.getRealmDefaultRole();
        this.baseURL = keycloakConfigurationProperties.getServerBaseUrl();
        this.clientId = keycloakConfigurationProperties.getRealmClientId();
        this.clientSecret = keycloakConfigurationProperties.getRealmClientSecret();
    }

    @Transactional
    public void createUser(UserDTO userDTO){
        String userId = UUID.randomUUID().toString();
        Address address = Address
                .builder()
                .id(UUID.randomUUID().toString())
                .city("Ahmad")
                .state("Gujarat")
                .country("India")
                .build();
        User user = User.builder()
                .id(userId)
                .role(userDTO.getRole())
                .email(userDTO.getEmailId())
                .name(userDTO.getName())
                .mobileNo("1234567891")
                .dateOfGraduation(userDTO.getDateOfGraduation())
                .address(address)
                .department(Department.CE)
                .build();

        UserRepresentation userRepresentation = userToUserRep(user);
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        if(response.getStatus() == 201) {
            log.info(String.valueOf(response.getStatus()) + response.getEntity());
            addRole(userId, userDTO.getRole());
            userRepository.save(user);
        }else{
            log.info("not able to create user check provided user details"+response.getStatus());
            throw new RuntimeException("not able to create user check provided user details");
        }
    }

    private UserRepresentation userToUserRep(User user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(user.getId());
        userRepresentation.setUsername(user.getId());
        userRepresentation.setFirstName(user.getName());
        userRepresentation.setLastName(user.getName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        List<CredentialRepresentation> credentialRepresentationList = new ArrayList<>();
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue("ams@12345");
        credentialRepresentationList.add(credentialRepresentation);
        userRepresentation.setCredentials(credentialRepresentationList);
        return userRepresentation;
    }

    public boolean addRole(String userId, Role role) throws DetailsNotFoundException {
        KeycloakUserDTO user = getUserByUserName(userId);
        RoleRepresentation roleRepresentation = keycloak.realm(realm).roles().get(String.valueOf(role)).toRepresentation();
        RoleRepresentation removeRoleRepresentation = keycloak.realm(realm).roles().get(defaultRole).toRepresentation();
        keycloak.realm(realm).users().get(user.getId()).roles().realmLevel().add(Collections.singletonList(roleRepresentation));
        keycloak.realm(realm).users().get(user.getId()).roles().realmLevel().remove(Collections.singletonList(removeRoleRepresentation));
        return true;
    }

    public KeycloakUserDTO getUserByUserName(String userId) throws DetailsNotFoundException {
        List<KeycloakUserDTO> listOfUsers = keycloak.realm(realm).users().searchByUsername(String.valueOf(userId), true).stream().map(this::userRepToUser).toList();
        if (listOfUsers.isEmpty()) {
            throw new DetailsNotFoundException("Can not find the user");
        }
        return listOfUsers.get(0);
    }

    private KeycloakUserDTO userRepToUser(UserRepresentation userRepresentation) {
        return KeycloakUserDTO.builder()
                .id(userRepresentation.getId())
                .email(userRepresentation.getEmail())
                .username(userRepresentation.getUsername())
                .build();
    }

    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) throws IOException {
        KeycloakUserDTO keycloakUser = getUserByEmail(loginRequestDTO.getEmailId());

        String username = keycloakUser.getUsername();
        String keycloakBaseUrl = baseURL;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(keycloakBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KeycloakLoginService keycloakApi = retrofit.create(KeycloakLoginService.class);
        Call<LoginResponseDTO> call = keycloakApi.getToken(
                "password", clientId, clientSecret, username, loginRequestDTO.getPassword(), realm);

        retrofit2.Response<LoginResponseDTO> response = call.execute();
        if (!response.isSuccessful()) {
            throw new IncorrectCredentialsException("Wrong email/password");
        }
        if (response.body() == null) {
            throw new ExternalServiceException("Something went wrong");
        }

        LoginResponseDTO tokenResponse = response.body();

        return LoginResponseDTO.builder()
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .build();
    }

    @Override
    public User getUserDetails(String userId) {
        return userRepository.findById(userId).orElseThrow(
                ()-> new UserNotFoundException("User not exist in db"));
    }

    public KeycloakUserDTO getUserByEmail(String emailId) {
        List<KeycloakUserDTO> listOfUsers = keycloak.realm(realm).users().searchByEmail(emailId, true).stream().map(this::userRepToUser).toList();

        if (listOfUsers.isEmpty()) {
            return null;
        }
        return listOfUsers.get(0);
    }
}
