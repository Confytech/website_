package com.example.demo.controller;

import com.example.demo.model.Entertainment;
import com.example.demo.service.EntertainmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/entertainment")
public class EntertainmentController {

    @Autowired
    private EntertainmentService entertainmentService;

    @GetMapping
    public List<Entertainment> getAllEntertainment() {
        return entertainmentService.getAllEntertainment();
    }

    // Add more endpoints if needed, e.g., to get specific entertainment content
}
