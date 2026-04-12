package com.example.homekeydoor.repositories;

import com.example.homekeydoor.entities.AbstractRemovableEntity;
import org.springframework.data.domain.Pageable;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRemovableRepository <T extends AbstractRemovableEntity, ID extends Serializable> extends JpaRepository<T, ID> {

    Long countByRemoved(boolean removed);

    List<T> findAllByRemoved(boolean removed);

    List<T> findAllByRemoved(Sort sort, boolean removed);

    Page<T> findByRemoved(Pageable pageable, boolean removed);

}
