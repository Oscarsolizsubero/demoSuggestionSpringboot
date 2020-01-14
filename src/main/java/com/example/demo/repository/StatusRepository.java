package com.example.demo.repository;

import com.example.demo.model.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {
    Status findByDescription(String description);
    @Query("select s from Status s where s.description in :descriptions")
    List<Status> findByDescriptionIn(@Param("descriptions") List<String> statusDescriptions);
}
