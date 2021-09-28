package com.jjbyun.daangn.dao;

import com.jjbyun.daangn.entity.VoteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteInfoRepository extends CrudRepository<VoteInfo, String> {

    Optional<VoteInfo> findByVoteId(String voteId);

    Optional<VoteInfo> findFirstByUserId(String userId);

    List<VoteInfo> findAllByUserId(String userId);
}
