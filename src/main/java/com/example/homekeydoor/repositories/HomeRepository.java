package com.example.homekeydoor.repositories;


import com.example.homekeydoor.entities.Home;
import org.springframework.stereotype.Repository;


@Repository
public interface HomeRepository  extends BaseRemovableRepository<Home, Long> {
}
