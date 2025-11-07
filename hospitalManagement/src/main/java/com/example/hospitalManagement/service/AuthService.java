package com.example.hospitalManagement.service;

import com.example.hospitalManagement.dto.LogginResponseDto;
import com.example.hospitalManagement.dto.LoginRequestDto;
import com.example.hospitalManagement.dto.SignupResponseDto;
import com.example.hospitalManagement.entity.User;
import com.example.hospitalManagement.entity.type.AuthProviderType;
import com.example.hospitalManagement.repository.UserRepository;
import com.example.hospitalManagement.security.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    public User signUpInternal(LoginRequestDto signupRequestDto, AuthProviderType authProviderType , String providerId){
        User user=  userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        if(user!= null) throw new IllegalArgumentException("user already exist..!!");


        user= User.builder()
                .username(signupRequestDto.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .build();

        if(authProviderType == AuthProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        }

        return user;
    }

    public SignupResponseDto signup(LoginRequestDto signupRequestDto) {
        User user=  signUpInternal(signupRequestDto,AuthProviderType.EMAIL,null);

        return new SignupResponseDto(user.getId(),user.getUsername());
    }

    @Transactional
    public ResponseEntity<LogginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        AuthProviderType authProviderType =authUtil.getAuthProviderTypeFromRegistrationId(registrationId);
        String providerId= authUtil.determineProviderIdFromOAuth2User(oAuth2User , registrationId );

        User user = (User) userRepository.findByProviderIdAndProviderType(providerId,authProviderType).orElse(null);
        String email = oAuth2User.getAttribute("email");

        User emailUser= userRepository.findByUsername(email).orElse(null);

        if(user ==null && emailUser == null){
            String username= authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId );
            user = signUpInternal(new LoginRequestDto(username,null),authProviderType, providerId);
        }else if (user!=null){
            if(email !=null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else{
            throw  new BadCredentialsException("This mail already existed with "+emailUser.getProviderType());
        }

        LogginResponseDto logginResponseDto = new LogginResponseDto(authUtil.generateAccessToken(user),user.getId());
        return ResponseEntity.ok(logginResponseDto);
    }
}
