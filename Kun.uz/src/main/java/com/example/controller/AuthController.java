package com.example.controller;

import com.example.dto.AuthDTO;
import com.example.dto.AuthResponseDTO;
import com.example.dto.register.RegistrationDTO;
import com.example.enums.LanguageEnum;
import com.example.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody AuthDTO dto,
                                    @RequestHeader(name = "Accept-Language", defaultValue = "RU") LanguageEnum lang) {
        AuthResponseDTO response = authService.login(dto, lang);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto) {
        String response = String.valueOf(authService.registration(dto));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verification/email/{jtwToken}")
    public ResponseEntity<?> emailVerification(@PathVariable("jtwToken") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }


}
