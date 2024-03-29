package com.example.dto;

import com.example.enums.ProfileRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ProfileRoleEnum role;

}
