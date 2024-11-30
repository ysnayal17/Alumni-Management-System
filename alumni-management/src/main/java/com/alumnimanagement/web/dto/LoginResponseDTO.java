package com.alumnimanagement.web.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;
}
