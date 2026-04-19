package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.HomeOwner;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeOwnerRepository  extends BaseRemovableRepository<HomeOwner, Long> {

    @Query(nativeQuery = true, value = "select * from home_owners where admin_id = :adminId")
    List<HomeOwner> findAllByAdminId(@Param("adminId") Long adminId);
}