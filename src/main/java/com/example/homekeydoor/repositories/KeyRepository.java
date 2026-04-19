package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.Key;
import org.springframework.stereotype.Repository;


@Repository
public interface KeyRepository extends BaseRemovableRepository<Key, Long> {
}
