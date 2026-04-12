package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.User;
import com.example.homekeydoor.security.RegistrationType;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends BaseRemovableRepository<User, Long>, QueryDslPredicateExecutor<User> {

    User findByEmailAndRegistrationTypeAndRemoved(String email, RegistrationType registered, boolean removed);

    User findByEmailAndRemoved(String email, boolean removed);

    User findByKeyAndRemoved(String key, boolean removed);
}
