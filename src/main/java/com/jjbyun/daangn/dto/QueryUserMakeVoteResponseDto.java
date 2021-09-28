package com.jjbyun.daangn.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
/*
* 특정 사용자가 작성한 투표 목록들 가져오기
* */
public class QueryUserMakeVoteResponseDto {
    String UserId;
    Map<String, String> userMakeVoteList;
}
