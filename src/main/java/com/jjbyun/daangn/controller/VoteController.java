package com.jjbyun.daangn.controller;

import com.jjbyun.daangn.dto.*;
import com.jjbyun.daangn.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService service;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/create")
    public CreateVoteResponseDto createVote(@RequestHeader Map<String, String> header,
                                            @RequestBody final CreateVoteRequestDto body) throws ParseException {
        String user_id = header.get("x-user-id");

        return service.createVote(user_id, body);
    }

    @GetMapping("/query/{articleId}/{voteId}")
    public QueryVoteResponseDto queryVote(@PathVariable("articleId") final String articleId,
                                          @PathVariable("voteId") final String voteId,
                                          @RequestHeader Map<String, String> header){
        //리턴하는객체 만들어주기 -> 작성자 id, 투표 제목, 투표 설명, 투표 항목, 마감시간, 투표여부, 투표 수
        String user_id = header.get("x-user-id");

        return service.queryVote(user_id, articleId, voteId);
    }
    @GetMapping("/query/userMake")
    public QueryUserMakeVoteResponseDto queryUserMakeVote(@RequestHeader Map<String, String> header){
        String user_id = header.get("x-user-id");

        return service.queryUserMakeVote(user_id);
    }
    @PutMapping("/select")
    public SelectVoteResponseDto selectVote(@RequestHeader Map<String, String> header,
                                            @RequestBody final SelectVoteRequestDto body){
        String user_id = header.get("x-user-id");

        return service.selectVote(user_id, body);
    }
}
