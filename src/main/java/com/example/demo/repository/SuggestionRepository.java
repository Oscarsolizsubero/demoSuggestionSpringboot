package com.example.demo.repository;

import com.example.demo.model.Status;
import com.example.demo.model.Suggestion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SuggestionRepository extends CrudRepository<Suggestion, Long> {
    Suggestion findById(long id);

    @Query("select s from Suggestion s where s.status in :ids order by s.quantityVote desc")
    List<Suggestion> findByStatusIdByOrderByQuantityVote(@Param("ids") List<Status> statusIds);

    List<Suggestion> findAllByOrderByQuantityVoteDesc();

}
