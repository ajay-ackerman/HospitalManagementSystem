package com.example.hospitalManagement.controller;

import com.example.hospitalManagement.dto.LogginResponseDto;
import com.example.hospitalManagement.dto.LoginRequestDto;
import com.example.hospitalManagement.dto.SignupResponseDto;
import com.example.hospitalManagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity <LogginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));

    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody LoginRequestDto signupRequestDto ){
        return  ResponseEntity.ok(authService.signup(signupRequestDto));
    }
}
