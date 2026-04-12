package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, QueryDslPredicateExecutor<Role> {

    Role findByName(String name);

    List<Role> findRolesByStoreRequiredEquals(boolean hasStore);
}