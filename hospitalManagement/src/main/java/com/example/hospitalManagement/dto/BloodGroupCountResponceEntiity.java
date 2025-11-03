package com.example.hospitalManagement.dto;

import com.example.hospitalManagement.entity.type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BloodGroupCountResponceEntiity {
    private BloodGroupType bloodGroupType;
    private Long count;
}
