package com.example.homekeydoor.dataservices;


import com.example.homekeydoor.repositories.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeDataService {

    @Autowired
    private HomeRepository homeRepository;
}
