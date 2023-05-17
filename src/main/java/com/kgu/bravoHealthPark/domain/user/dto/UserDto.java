package com.kgu.bravoHealthPark.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String loginId;

    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 3, max = 15)
    @Column(unique = true)
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<AuthorityDto> authorityDtoSet;

    public static UserDto from(User user) {
        if (user == null) return null;

        return UserDto.builder()
                .username(user.getUsername())
                .loginId(user.getLoginId())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
