package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.enums.Language;
import com.example.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/articleType")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody ArticleTypeDTO articleType) {
        ArticleTypeDTO response = articleTypeService.create(articleType);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ArticleTypeDTO articleType) {
        return ResponseEntity.ok(articleTypeService.update(id, articleType));
    }

    @GetMapping("/byLang")
    public ResponseEntity<?> getByLang(@RequestParam Language lang){
        List<ArticleTypeDTO> response = articleTypeService.getByLang(lang);
        return ResponseEntity.ok(response);

    }
}
