package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.base.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long>, QuerydslPredicateExecutor<Privilege> {

    Privilege findByName(String name);

}