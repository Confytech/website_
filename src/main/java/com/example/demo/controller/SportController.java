package com.example.demo.controller;

import com.example.demo.model.Sport;
import com.example.demo.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sports")
public class SportController {

    @Autowired
    private SportService sportService;

    @GetMapping
    public List<Sport> getAllSports() {
        return sportService.getAllSports();
    }

    // Add more endpoints if needed, e.g., to get specific sports data
}
