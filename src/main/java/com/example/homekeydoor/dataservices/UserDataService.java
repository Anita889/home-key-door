package com.example.homekeydoor.dataservices;


import com.example.homekeydoor.entities.Role;
import com.example.homekeydoor.entities.User;
import com.example.homekeydoor.repositories.UserRepository;
import com.example.homekeydoor.security.RegistrationType;
import com.google.common.collect.Lists;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserDataService extends AbstractRemovableDataService<User, Long> {
    @Autowired
    public UserDataService(UserRepository userRepository) {
        super(userRepository);
    }

    @Autowired
    private UserRepository userRepository;

    public User findByEmailAndRegistrationType(String email, RegistrationType registered) {
        return userRepository.findByEmailAndRegistrationTypeAndRemoved(email, registered, false);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailAndRemoved(email, false);
    }

    public User findByKey(String key) {
        return userRepository.findByKeyAndRemoved(key, false);
    }

    public Page<User> findByFilters(List<Role> roles, Pageable pageable){
        List<Predicate> predicates = new ArrayList<>();
        addFilteredPredicates(Collections.emptyList(), roles, predicates);

        Page<User> response = userRepository.findAll(ExpressionUtils.allOf(predicates), pageable);
        return response;
    }

    public Page<User> findByFilters(Collection<User> userEntityCollection, List<Role> roles, Pageable pageable){
        List<Predicate> predicates = new ArrayList<>();
        addFilteredPredicates(userEntityCollection, roles, predicates);

        Page<User> response = userRepository.findAll(ExpressionUtils.allOf(predicates), pageable);
        return response;
    }

    public List<User> findByRoleExludeExistingUsers(Role role, Collection<User> userEntityCollection){
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(QUser.userEntity.roles.contains(role));
        predicates.add(QUser.userEntity.removed.isFalse());
        if(userEntityCollection != null && !userEntityCollection.isEmpty()){
            predicates.add(QUser.userEntity.notIn(userEntityCollection));
        }
        return Lists.newArrayList(userRepository.findAll(ExpressionUtils.allOf(predicates)));
    }


    private void addFilteredPredicates(Collection<User> userEntityCollection, List<Role> roles, List<Predicate> predicates){
        predicates.add(QUser.userEntity.removed.isFalse());
        if(userEntityCollection != null && !userEntityCollection.isEmpty()){
            predicates.add(QUser.userEntity.in(userEntityCollection));
        }

        if(roles != null){
            for(Role role : roles){
                predicates.add(QUser.userEntity.roles.contains(role));
            }
        }

    }
}
