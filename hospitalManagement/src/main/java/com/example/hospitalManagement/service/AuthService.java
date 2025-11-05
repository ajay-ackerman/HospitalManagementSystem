package com.example.hospitalManagement.service;

import com.example.hospitalManagement.dto.LogginResponseDto;
import com.example.hospitalManagement.dto.LoginRequestDto;
import com.example.hospitalManagement.dto.SignupResponseDto;
import com.example.hospitalManagement.entity.User;
import com.example.hospitalManagement.repository.UserRepository;
import com.example.hospitalManagement.security.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private  final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;

    public LogginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );
        User user= (User) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(user);

        return new LogginResponseDto(token, user.getId());
    }

    public SignupResponseDto signup(LoginRequestDto signupRequestDto) {
        User user=  userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        if(user!= null) throw new IllegalArgumentException("user already exist..!!");
        user= userRepository.save(User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .build());

        return new SignupResponseDto(user.getId(),user.getUsername());
    }
}
