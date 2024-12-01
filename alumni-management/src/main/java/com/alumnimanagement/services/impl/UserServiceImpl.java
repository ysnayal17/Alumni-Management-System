package com.alumnimanagement.services.impl;

import com.alumnimanagement.config.KeycloakConfigurationProperties;
import com.alumnimanagement.config.UserDetailsConfiguration;
import com.alumnimanagement.entity.Address;
import com.alumnimanagement.entity.Company;
import com.alumnimanagement.entity.User;
import com.alumnimanagement.enums.Department;
import com.alumnimanagement.enums.Role;
import com.alumnimanagement.exception.DetailsNotFoundException;
import com.alumnimanagement.exception.ExternalServiceException;
import com.alumnimanagement.exception.IncorrectCredentialsException;
import com.alumnimanagement.exception.InvalidDataException;
import com.alumnimanagement.exception.UserNotFoundException;
import com.alumnimanagement.repo.UserRepository;
import com.alumnimanagement.services.def.KeycloakLoginService;
import com.alumnimanagement.services.def.UserService;
import com.alumnimanagement.util.ObjectMapperUtil;
import com.alumnimanagement.web.dto.KeycloakUserDTO;
import com.alumnimanagement.web.dto.LoginRequestDTO;
import com.alumnimanagement.web.dto.LoginResponseDTO;
import com.alumnimanagement.web.dto.UserDTO;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    private final EmailService emailService;
    private final MapperFacade mapperFacade;

    @Autowired
    public UserServiceImpl(Keycloak keycloak, UserRepository userRepository, KeycloakConfigurationProperties keycloakConfigurationProperties, EmailService emailService, MapperFacade mapperFacade) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
        this.realm = keycloakConfigurationProperties.getApplicationRealm();
        this.defaultRole = keycloakConfigurationProperties.getRealmDefaultRole();
        this.baseURL = keycloakConfigurationProperties.getServerBaseUrl();
        this.clientId = keycloakConfigurationProperties.getRealmClientId();
        this.clientSecret = keycloakConfigurationProperties.getRealmClientSecret();
        this.emailService = emailService;
        this.mapperFacade = mapperFacade;
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
                .email(userDTO.getEmail())
                .password("ami@12345")
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
        credentialRepresentation.setValue(user.getPassword());
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
    public UserDTO getUserDetails(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new UserNotFoundException("User not exist in db"));
        return mapperFacade.map(user,UserDTO.class);
    }

    @Override
    public List<UserDTO> getUserList() {

        List<User> userList = this.userRepository.findAll();
        return userList.stream().map(user->mapperFacade.map(user, UserDTO.class)).toList();
    }

    @Override
    public boolean logout(String refreshToken, String userId) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KeycloakLoginService keycloakService = retrofit.create(KeycloakLoginService.class);
        Call<String> call = keycloakService.logout(clientId, clientSecret, refreshToken, realm);
        retrofit2.Response<String> response = call.execute();

        KeycloakUserDTO user = getUserByUserName(userId);
        keycloak.realm(realm).users().get(user.getId()).logout();

        return response.isSuccessful();
    }

    @Override
    @Transactional
    public void addUsersFromFile(MultipartFile file) {
        List<JsonNode> jsonNodes = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            DataFormatter formatter = new DataFormatter();

            List<String> headers = new ArrayList<>();

            if (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                row.forEach(cell -> {
                    String header = formatter.formatCellValue(cell);
                    if (!Objects.equals(header, "")) {
                        headers.add(header);
                    }
                });
            }

            int index = 2;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                List<String> values = new ArrayList<>();
                row.forEach(cell -> {
                        values.add(formatter.formatCellValue(cell));});
                JsonNode jsonNode = ObjectMapperUtil.toObject(createMap(headers, values), JsonNode.class);
                if (!jsonNode.isEmpty()) {
                    jsonNodes.add(jsonNode);
                }else{
                    throw new InvalidDataException(String.format("Unable to read data at the row [%s]",index));
                }
                index++;
            }

        } catch (IOException ex) {
            throw new InvalidDataException(String.format("Not able to read file: %s", ex.getMessage()));
        } catch (InvalidDataException ex) {
            throw new InvalidDataException(ex.getMessage());
        }
        List<User> userList = mapJsonToPojos(jsonNodes);
        try {
            for(User user:userList){
                user.setId(UUID.randomUUID().toString());
                user.setPassword(KeyGenerators.string().generateKey());
                if(user.getDateOfGraduation().isBefore(LocalDate.now())){
                    user.setRole(Role.ALUMNI);
                }else{
                    user.setRole(Role.STUDENT);
                }
                log.info(user.toString());
            }
            this.userRepository.saveAll(userList);
        } catch (Exception e) {
            throw new RuntimeException("Error encountered while adding userlist to db: "+e.getMessage(),e);
        }

        for(User user:userList){
            createUserInKeycloak(user);
            this.emailService.sendMail(user.getEmail(),user.getPassword());
        }
    }

    @Override
    @Transactional
    public void updateUserDetails(UserDTO userDTO) throws IOException {
        User user = this.userRepository.findById(userDTO.getId()).orElseThrow(()->new UserNotFoundException("User not found"));
        user.setLinkedinProfile(userDTO.getLinkedinProfile());
        user.setDepartment(userDTO.getDepartment());
        user.setSkills(userDTO.getSkills());
        if(userDTO.getImage() != null){
            user.setProfileImage(userDTO.getImage().getBytes());
        }
        if(userDTO.getCompanyName() != null) {
            Company company = Company.builder()
                    .id(UUID.randomUUID().toString())
                    .modified(LocalDateTime.now())
                    .name(userDTO.getCompanyName())
                    .build();
            user.setCompany(company);
        }
        if(userDTO.getCity() != null) {
            Address address = Address
                    .builder()
                    .id(UUID.randomUUID().toString())
                    .city(userDTO.getCity())
                    .country(userDTO.getCountry())
                    .state(userDTO.getState())
                    .build();
            user.setAddress(address);
        }

        try {
            this.userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error while updating user details: "+e.getMessage(),e);
        }
    }

    private void createUserInKeycloak(User user){
        UserRepresentation userRepresentation = userToUserRep(user);
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        if(response.getStatus() == 201) {
            log.info(String.valueOf(response.getStatus()) + response.getEntity());
            addRole(user.getId(), user.getRole());
            userRepository.save(user);
        }else{
            log.info("not able to create user check provided user details");
            throw new RuntimeException("not able to create user check provided user details");
        }
    }

    private List<User> mapJsonToPojos(List<JsonNode> jsonNodes){
        List<User> pojoList = new ArrayList<>();

        for (JsonNode jsonNode : jsonNodes) {
            User pojo = new User();

            Map<String,Class<?>> datatype = UserDetailsConfiguration.getDatatype();
            UserDetailsConfiguration.getBasicHeaders().forEach(
                    (jsonFieldHeader,entityField)->{
                        JsonNode jsonField = jsonNode.get(jsonFieldHeader);
                        if (jsonField != null) {
                            try {
                                Field field = User.class.getDeclaredField(entityField);
                                field.setAccessible(true);
                                Object value = convertValue(jsonField, datatype.get(entityField),jsonFieldHeader);
                                field.set(pojo, value);
                            } catch (NoSuchFieldException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
            );
            pojoList.add(pojo);
        }
        return pojoList;
    }

    private static Object convertValue(JsonNode jsonField, Class<?> typeClass,String header) {
        try {
            if (typeClass == LocalDate.class) {
                return LocalDate.parse(jsonField.asText());
            } else if (typeClass == String.class) {
                return jsonField.asText();
            } else if (typeClass == Department.class) {
                return Department.valueOf(jsonField.asText());
            } else {
                throw new IllegalArgumentException("Unsupported type: " + typeClass);
            }

        } catch (Exception e) {
            throw new RuntimeException("Invalid datatype for "+header +":"+e.getMessage());

        }
    }

    private Map<String, String> createMap(List<String> headers, List<String> values) {
        Map<String, String> map = new HashMap<>();
        if (headers.size() <= values.size()) {
            for (int i = 0; i < headers.size(); i++) {
                map.put(headers.get(i), values.get(i));
            }
        }
        return map;
    }

    public KeycloakUserDTO getUserByEmail(String emailId) {
        List<KeycloakUserDTO> listOfUsers = keycloak.realm(realm).users().searchByEmail(emailId, true).stream().map(this::userRepToUser).toList();

        if (listOfUsers.isEmpty()) {
            return null;
        }
        return listOfUsers.get(0);
    }
}
