package com.empresaurios.stylist.bean;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import com.empresaurios.stylist.provider.CustomPasswordProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.PasswordType;
import io.quarkus.security.jpa.Roles;

import lombok.*;

@Entity
@Table(name = "st_users", schema = "stylist")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Users extends PanacheEntityBase{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID user_id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    @Password(value = PasswordType.CUSTOM, provider = CustomPasswordProvider.class)
    @JsonIgnore
    private String password;

    @Column(name = "surnames")
    private String surnames;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Roles
    private String role;

    @Column(name = "email")
    private String email;

}

