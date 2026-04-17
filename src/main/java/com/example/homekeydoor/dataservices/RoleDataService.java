package com.example.homekeydoor.dataservices;


import com.example.homekeydoor.entities.base.Role;

import com.example.homekeydoor.repositories.RoleRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.ExpressionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleDataService {

    @Autowired
    private RoleRepository roleRepository;
//
//    @Autowired
//    private PrivilegeRepository privilegeRepository;

    public Role save(Role object) {
        return roleRepository.save(object);
    }

    public void remove(Role object) {
        roleRepository.delete(object);
    }

    public void removeById(Long id) {
        roleRepository.delete(id);
    }

    public Role findById(Long id) {
        return roleRepository.findOne(id);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public long count() {
        return roleRepository.count();
    }

    public List<Role> findByIdList(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(QRole.role.id.in(roleIds));

        return Lists.newArrayList(roleRepository.findAll(ExpressionUtils.allOf(predicates)));
    }

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }

//    public Role findSupplierAdminRole(){
//        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(QRole.role.userScope.eq(UserScopeType.USER_SCOPE_SUPPLIER));
//        predicates.add(QRole.role.privileges.contains(privilegeRepository.findByName(PrivilegeType.SUP_COMPANY_IS_ADMIN)));
//        return roleRepository.findOne(ExpressionUtils.allOf(predicates));
//    }
//
//    public Role findRetailerAdminRole(){
//        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(QRole.role.userScope.eq(UserScopeType.USER_SCOPE_RETAILER));
//        predicates.add(QRole.role.privileges.contains(privilegeRepository.findByName(PrivilegeType.RET_COMPANY_IS_ADMIN)));
//        return roleRepository.findOne(ExpressionUtils.allOf(predicates));
//    }
//
//    public List<Role> findRolesByStoreRequiredEquals(boolean hasStore) {
//        return roleRepository.findRolesByStoreRequiredEquals(hasStore);
//    }
}

