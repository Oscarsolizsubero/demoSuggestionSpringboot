package com.example.demo.repository;

import com.example.demo.model.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {
    Status findByName(String name);
    @Query("select s from Status s where s.name in :descriptions")
    List<Status> findByNameIn(@Param("descriptions") List<String> statusDescriptions);
}
