package com.jjbyun.daangn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class QueryVoteResponseDto {
    String userId;
    String voteTitle;
    String voteDescription;
    List<String> voteSelection;
    Date voteDeadLine;
    boolean isUserSelect;
    Map<String, Integer> voteSelectCount;
}
