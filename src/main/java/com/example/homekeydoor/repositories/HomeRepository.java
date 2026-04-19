package com.example.homekeydoor.repositories;


import com.example.homekeydoor.entities.Home;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public interface HomeRepository  extends BaseRemovableRepository<Home, Long> {
    List<Home> findAllByOwnerIdAndRemovedFalse(Long ownerId);
}
