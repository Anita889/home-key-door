package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.HomeOwner;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeOwnerRepository  extends BaseRemovableRepository<HomeOwner, Long> {
    List<HomeOwner> findAllByAdminIdAndRemovedFalse(Long adminId);
}
