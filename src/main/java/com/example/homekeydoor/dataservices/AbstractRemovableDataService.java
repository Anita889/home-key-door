package com.example.homekeydoor.dataservices;

import com.example.homekeydoor.entities.AbstractRemovableEntity;
import com.example.homekeydoor.repositories.BaseRemovableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * Created by garik
 */
public abstract class AbstractRemovableDataService<T extends AbstractRemovableEntity, ID extends Serializable> implements RemovableDataService<T, ID> {

    protected BaseRemovableRepository<T, ID> repository;

    public AbstractRemovableDataService(BaseRemovableRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T object) {
        return repository.save(object);
    }

    @Override
    public void remove(T object) {
        repository.delete(object);
    }

    @Override
    public void removeById(ID id) {
        repository.delete(id);
    }

    @Override
    public T findById(ID id) {
        return repository.findOne(id);
    }

    public Iterable<T> saveAll(Iterable<T> objects) {
        return repository.save(objects);
    }

    public void remove(T object, boolean hard) {
        if (hard) {
            remove(object);
        } else if (!object.isRemoved()) {
            object.setRemoved(true);
            save(object);
        }
    }

    public void removeById(ID id, boolean hard) {
        if (hard) {
            removeById(id);
        } else {
            T object = findById(id, false);
            if (!object.isRemoved()) {
                object.setRemoved(true);
                save(object);
            }
        }
    }

    public T findById(ID id, boolean hard) {
        if (hard) {
            return findById(id);
        }
        T object = findById(id);
        return (object != null && !object.isRemoved()) ? object : null;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    public long count(boolean hard) {
        if (!hard) {
            return repository.countByRemoved(hard);
        }
        return count();
    }

    @Override
    public long count() {
        return repository.count();
    }

    public List<T> findAll(boolean hard) {
        if (!hard) {
            return repository.findAllByRemoved(hard);
        }
        return findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public List<T> findAll(Sort sort, boolean hard) {
        if (!hard) {
            repository.findAllByRemoved(sort, hard);
        }
        return findAll(sort);
    }

    public Page<T> findPageable(Pageable pageable, boolean hard) {
        if (!hard) {
            repository.findByRemoved(pageable, hard);
        }
        return findPageable(pageable);

    }

    @Override
    public Page<T> findPageable(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
