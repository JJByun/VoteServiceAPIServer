package com.jjbyun.daangn.dao;

import com.jjbyun.daangn.entity.VoteList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteListRepository extends CrudRepository<VoteList, Integer> {
    Optional<VoteList> findByUserIdAndVoteId(String userId, String voteId);

    long countByVoteIdAndVoteSelect(String voteId, String voteSelect);
}
