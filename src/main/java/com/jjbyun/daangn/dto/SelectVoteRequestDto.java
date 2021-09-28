package com.jjbyun.daangn.dto;

import com.jjbyun.daangn.entity.VoteList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SelectVoteRequestDto {
    String articleId;
    String voteId;
    String voteSelection;

    public SelectVoteRequestDto(SelectVoteRequestDto body) {
        this.articleId = body.articleId;
        this.voteId = body.voteId;
        this.voteSelection = body.voteSelection;
    }

    public VoteList toEntity(String userId){
        return VoteList.builder()
                .userId(userId)
                .voteId(voteId)
                .voteSelect(voteSelection)
                .build();
    }
}
