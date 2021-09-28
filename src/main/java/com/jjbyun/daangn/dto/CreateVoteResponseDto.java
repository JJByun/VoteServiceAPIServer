package com.jjbyun.daangn.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConstructorBinding;

@Builder
@Getter
public class CreateVoteResponseDto {
    String  voteId;
}
