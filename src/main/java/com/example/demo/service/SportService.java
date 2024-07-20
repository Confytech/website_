package com.example.demo.service;


import com.example.demo.model.Sport;
import com.example.demo.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    // Add more business logic if needed
}
