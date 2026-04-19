package com.example.homekeydoor.dataservices;


import com.example.homekeydoor.repositories.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyDataService {

    @Autowired
    private KeyRepository keyRepository;
}
