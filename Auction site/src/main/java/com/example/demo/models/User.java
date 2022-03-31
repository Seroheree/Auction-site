package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

    @Column(unique = true)
    @Email
    @NotBlank(message = "email cant be null")
        private String email;

    @NotBlank(message = "password can't be null")
        private String password;

    @Digits(integer = 9, fraction = 0)
    @Size(min=9, max=9, message="phone number should have 9 digits")
    @NotBlank(message = "phone number cant be empty")
        private String phoneNumber;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
