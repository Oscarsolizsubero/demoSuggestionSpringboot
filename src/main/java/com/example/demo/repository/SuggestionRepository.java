package com.example.demo.repository;

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
    @Query("select s from Suggestion s where s.status.id=:id")
    List<Suggestion> findByStatusId(@Param("id") long statusId);

    boolean existsByUserId(long id);
    @Modifying
    @Transactional
    @Query(value ="insert into votes_like (user_id, suggestion_id) values (:user_id, :suggestion_id)",
            nativeQuery = true)
    void insertVote(@Param("user_id") Long userId, @Param("suggestion_id") Long suggestionId);
    @Query(value="delete from votes_like where user_id=:user_id and suggestion_id=:suggestion_id",nativeQuery = true)
    void deleteVote(@Param("user_id") Long userId, @Param("suggestion_id") Long suggestionId);
}
