package com.example.dto;

import com.example.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class JwtDTO {
    private Integer id;
    private ProfileRole role;

}
