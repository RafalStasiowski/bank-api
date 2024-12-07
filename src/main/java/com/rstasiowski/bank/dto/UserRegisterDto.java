package com.rstasiowski.bank.dto;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
