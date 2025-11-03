package com.example.hospitalManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.print.Doc;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 100)
    private String name;

    @OneToOne
    private Doctor headDoctor;

    @ManyToMany
    @JoinTable(
            name = "dpt_doctors"
    )
    private Set<Doctor> doctors= new HashSet<>();


}
