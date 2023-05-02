package com.kgu.bravoHealthPark.domain.user.controller;

import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.user.dto.UserDto;
import com.kgu.bravoHealthPark.domain.user.dto.UserForm;
import com.kgu.bravoHealthPark.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"User Api"}, description = "유저 관련 Api (#7)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "유저 생성")
    @PostMapping("/create")
    public ResponseEntity<UserDto> createAlarm(@RequestBody UserForm form) {
        User user = User.builder()
                .phoneNumber(form.getPhoneNumber())
                .name(form.getName())
                .build();

        userService.join(user);

        UserDto userDto = new UserDto(user);

        return ResponseEntity.ok().body(userDto);
    }

    @ApiOperation(value = "유저 삭제")
    @PostMapping("/delete")
    public ResponseEntity createAlarm(User user) {
        userService.delete(user);

        return ResponseEntity.ok().body(null);
    }


}
