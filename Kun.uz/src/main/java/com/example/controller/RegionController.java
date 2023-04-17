package com.example.controller;

import com.example.service.RegionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    private RegionService regionService;

}
