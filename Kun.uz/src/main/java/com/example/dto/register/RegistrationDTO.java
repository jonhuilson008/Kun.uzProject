package com.example.dto.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
}
