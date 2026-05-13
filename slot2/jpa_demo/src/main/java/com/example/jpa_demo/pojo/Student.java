package com.example.jpa_demo.pojo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;
    @Column(unique = true)
    private String email;
    private String age;

    public Student() {
    }

    public Student(Integer id, String fullName, String email, String age) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.age = age;
    }
}
