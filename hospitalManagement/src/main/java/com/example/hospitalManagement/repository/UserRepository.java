package com.example.hospitalManagement.repository;

import com.example.hospitalManagement.entity.User;
import com.example.hospitalManagement.entity.type.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);


    Optional<Object> findByProviderIdAndProviderType(String providerId, AuthProviderType authProviderType);
}