package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.RegionDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.RegionService;
import com.example.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    private RegionService regionService;
    @PostMapping("/admin")
    public ResponseEntity<?> create(  @RequestBody RegionDTO dto){
        return ResponseEntity.ok(regionService.create(dto));
    }
    @GetMapping("/byLang")
    public ResponseEntity<?> getByLang(@RequestParam Language lang){
        List<RegionDTO> response = regionService.getByLang(lang);
        return ResponseEntity.ok(response);

    }
}
