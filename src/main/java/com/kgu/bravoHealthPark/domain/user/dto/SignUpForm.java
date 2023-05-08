package com.kgu.bravoHealthPark.domain.user.dto;

import com.kgu.bravoHealthPark.domain.user.domain.Role;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@Builder
public class SignUpForm {

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private int age;


    @Builder
    public User toEntity(String encPhoneNumber){
        return User.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .age(age)
                .encPhoneNumber(encPhoneNumber)
                .role(Role.USER)
                .build();
    }
}