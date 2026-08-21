package com.example.activity_tracker_monitor.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role;      // EMPLOYEE / MANAGER / ADMIN

    private Long managerId;   // nullable for top-level employees/admins
}
