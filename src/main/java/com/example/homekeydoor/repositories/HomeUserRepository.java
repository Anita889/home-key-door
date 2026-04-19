package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.HomeUser;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface HomeUserRepository  extends BaseRemovableRepository<HomeUser, Long> {

    @Query(nativeQuery = true, value = "select * from home_users where admin_id = :adminId")
    List<HomeUser> findAllByHomesAdminId(@Param("adminId") Long adminId);
}
