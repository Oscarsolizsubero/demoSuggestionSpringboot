package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_generator")
    @SequenceGenerator(name = "status_generator", initialValue = 100)
    private long id;
    @Column(nullable = false)
    private String description ;
}
