package com.kgu.bravoHealthPark.domain.user.dto;

import com.kgu.bravoHealthPark.domain.user.domain.User;
import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String phoneNumber;

    public UserDto(User user) {
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
    }
}

