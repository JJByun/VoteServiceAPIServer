package com.jjbyun.daangn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
/**
 * voteId: 투표 id
 * userId: 사용자 id
 * articleId: 게시글 id
 * voteTitle: 투표 제목
 * voteDescription: 투표 설명
 * voteSelection : 투표 항목 ( ',' 구분)
 * voteDeadline: 투표 마감 시간
 * */
public class VoteInfo {
    @Id @Column(length = 10, unique = true, nullable = false)
    String voteId;

    @Column(nullable = false, length = 4)
    String userId;

    @Column(nullable = false)
    Long articleId;

    @Column(nullable = false, length = 100)
    String voteTitle;

    @Column(nullable = true, length = 10000)
    String voteDescription;

    @Column(length = 5000)
    String voteSelection;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    Date voteDeadLine;
}
