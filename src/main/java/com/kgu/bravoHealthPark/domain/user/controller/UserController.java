package com.kgu.bravoHealthPark.domain.user.controller;

import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.user.dto.LoginForm;
import com.kgu.bravoHealthPark.domain.user.dto.UserDto;
import com.kgu.bravoHealthPark.domain.user.dto.SignUpForm;
import com.kgu.bravoHealthPark.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"User Api"}, description = "유저 관련 Api (#7)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "유저 생성")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignUpForm form) {
        User user = User.builder()
                .phoneNumber(form.getPhoneNumber())
                .name(form.getName())
                .build();

        userService.join(user);

        UserDto userDto = new UserDto(user);

        return ResponseEntity.ok().body(userDto);
    }

    @ApiOperation(value = "유저 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(User user) {
        userService.delete(user);

        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "회원 가입")
    @PostMapping("/signUp")
    public Long signUp(@RequestBody @Valid SignUpForm signUpForm) throws Exception {
        return userService.signup(signUpForm);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        String loginToken = userService.login(loginForm);
        return ResponseEntity.ok().body(loginToken);
    }

}
