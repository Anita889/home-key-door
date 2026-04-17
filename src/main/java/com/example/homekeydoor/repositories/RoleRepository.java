package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.base.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role> {

    Role findByName(String name);

    List<Role> findRolesByStoreRequiredEquals(boolean hasStore);
}