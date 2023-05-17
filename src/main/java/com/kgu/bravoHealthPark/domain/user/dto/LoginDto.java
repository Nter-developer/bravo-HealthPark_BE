package com.kgu.bravoHealthPark.domain.user.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String loginId;

    @NotNull
    @Size(min = 3, max = 100)
    private String phoneNumber;
}
