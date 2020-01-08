package com.example.demo.repository;

import com.example.demo.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {
    Status findByDescription(String description);
}
