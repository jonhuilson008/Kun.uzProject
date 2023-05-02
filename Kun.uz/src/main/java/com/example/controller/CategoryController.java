package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.RegionDTO;
import com.example.enums.LanguageEnum;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/admin/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO category) {
        CategoryDTO response = categoryService.create(category);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/admin/list")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        List<CategoryDTO> list = categoryService.getAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping(value = "/admin/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody CategoryDTO category) {
        return ResponseEntity.ok(categoryService.update(id, category));
    }

    @GetMapping("/admin/byLang")
    public ResponseEntity<?> getByLang(@RequestParam LanguageEnum lang){
        List<CategoryDTO> response = categoryService.getByLang(lang);
        return ResponseEntity.ok(response);

    }
    @DeleteMapping(value = "/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

}

