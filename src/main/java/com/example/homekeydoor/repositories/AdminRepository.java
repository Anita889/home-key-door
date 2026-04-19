package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository  extends BaseRemovableRepository<Admin, Long> {
}
