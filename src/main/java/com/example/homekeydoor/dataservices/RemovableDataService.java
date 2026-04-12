package com.example.homekeydoor.dataservices;

import com.example.homekeydoor.entities.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * Created by garik
 */
public interface RemovableDataService<T extends AbstractEntity, ID extends Serializable> {
    T save(T object);

    void remove(T object);

    void removeById(ID id);

    T findById(ID id);

    void remove(T object, boolean hard);

    void removeById(ID id, boolean hard);

    T findById(ID id, boolean hard);

    List<T> findAll(Sort sort, boolean hard);

    List<T> findAll(boolean hard);

    List<T> findAll(Sort sort);

    List<T> findAll();

    Page<T> findPageable(Pageable pageable, boolean hard);

    Page<T> findPageable(Pageable pageable);

    long count(boolean hard);

    long count();
}
