package org.qbit.applicationmanager.infrastructure.http.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDto {
    @NotBlank
    private String userName;
    private String rawPassword;

    public UserDto() {
    }

    public UserDto(String userName, String rawPassword) {
        this.userName = userName;
        this.rawPassword = rawPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }
}