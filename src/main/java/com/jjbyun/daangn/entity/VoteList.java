package com.jjbyun.daangn.entity;

import lombok.*;

import javax.persistence.*;


@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
/**
 * idx: pk를 위한 enum 값
 * userId: 사용자 id
 * voteSelect: 투표 항목 선택 값
 * voteId: 투표 id
 */
public class VoteList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idx;

    @Column(length = 4)
    String userId;

    @Column(length = 50)
    String voteSelect;

    @Column(length=10)
    String voteId;
}
