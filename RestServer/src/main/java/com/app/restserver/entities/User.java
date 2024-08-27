package com.app.restserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class User extends BaseEntity{
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    @JsonIgnore
    private boolean isActive;

    @Column(name = "last_login")
    @JsonIgnore
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Pipe> pipes;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Execution> executions;


    public User(String username, String email, String hashedPassword) {
        this.username = username;
        this.email = email;
        this.password = hashedPassword;
    }

    @PrePersist
    public void setDefaultValuesForInsert() {
        this.isActive = true;
    }
}
