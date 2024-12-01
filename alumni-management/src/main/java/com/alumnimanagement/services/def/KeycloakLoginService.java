package com.alumnimanagement.services.def;

import com.alumnimanagement.web.dto.LoginResponseDTO;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface KeycloakLoginService {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/realms/{realm}/protocol/openid-connect/token")
    Call<LoginResponseDTO> getToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("username") String username,
            @Field("password") String password,
            @retrofit2.http.Path("realm") String realm
    );

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/realms/{realm}/protocol/openid-connect/logout")
    Call<String> logout(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("refresh_token") String refreshToken,
            @retrofit2.http.Path("realm") String realm
    );

}

