package com.example.homekeydoor.dataservices;


import com.example.homekeydoor.consts.RegistrationType;
import com.example.homekeydoor.entities.User;
import com.example.homekeydoor.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserDataService{

    @Autowired
    private UserRepository userRepository;


    public User findByEmailAndRegistrationType(String email, RegistrationType registrationType) {
        return userRepository.findByEmailAndRegistrationTypeAndRemoved(email, registrationType, false);
    }

    public User findByKey(String key) {
        return userRepository.findByKeyAndRemoved(key, false);
    }

    public User save(User userEntity) {
        return userRepository.save(userEntity);
    }
}
