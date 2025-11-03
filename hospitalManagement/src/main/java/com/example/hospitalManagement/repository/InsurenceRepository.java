package com.example.hospitalManagement.repository;

import com.example.hospitalManagement.entity.Insurence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface InsurenceRepository extends JpaRepository<Insurence, Long> {
}