package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.RegionDTO;
import com.example.enums.Language;
import com.example.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

       @PostMapping(value = "/admin/create")
    public ResponseEntity<?> create(@RequestBody RegionDTO regionDTO) {
           RegionDTO response = regionService.create(regionDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/admin/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody RegionDTO regionDTO) {
        return ResponseEntity.ok(regionService.update(id, regionDTO));
    }

    @GetMapping("/admin/byLang")
    public ResponseEntity<?> getByLang(@RequestParam Language lang){
        List<RegionDTO> response = regionService.getByLang(lang);
        return ResponseEntity.ok(response);

    }
    @DeleteMapping(value = "/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(regionService.delete(id));
    }
    @GetMapping("/admin/list")
    public ResponseEntity<List<RegionDTO>> getAll() {
        List<RegionDTO> list = regionService.getAll();
        return ResponseEntity.ok(list);
    }


}
