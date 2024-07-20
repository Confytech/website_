package com.example.demo.service;


import com.example.demo.model.Entertainment;
import com.example.demo.repository.EntertainmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntertainmentService {

    @Autowired
    private EntertainmentRepository entertainmentRepository;

    public List<Entertainment> getAllEntertainment() {
        return entertainmentRepository.findAll();
    }

    // Add more business logic if needed
}
