package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.Key;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface KeyRepository extends BaseRemovableRepository<Key, Long> {
    List<Key> findAllByHomeUserIdAndRemovedFalse(Long homeUserId);

    @Query("""
            select k
            from Key k
            where k.removed = false
              and k.home.owner.id = :ownerId
            """)
    List<Key> findAllByOwnerId(@Param("ownerId") Long ownerId);
}
