package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.HomeUser;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public interface HomeUserRepository  extends BaseRemovableRepository<HomeUser, Long> {
    List<HomeUser> findAllByRemovedFalse();
}
