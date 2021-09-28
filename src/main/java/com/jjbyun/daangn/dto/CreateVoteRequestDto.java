package com.jjbyun.daangn.dto;

import com.jjbyun.daangn.entity.VoteInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateVoteRequestDto {
    long articleId;
    String voteTitle;
    String voteDescription;
    List<String> voteSelection;
    Integer voteDeadLine;

    public CreateVoteRequestDto(CreateVoteRequestDto body){
        this.articleId = body.articleId;
        this.voteTitle = body.voteTitle;
        this.voteDescription = body.voteDescription;
        this.voteSelection = body.voteSelection;
        this.voteDeadLine = body.voteDeadLine;
    }

    public VoteInfo toEntity(String userId, String voteId){
        return VoteInfo.builder()
                .userId(userId)
                .voteId(voteId)
                .articleId(articleId)
                .voteTitle(voteTitle)
                .voteDescription(voteDescription)
                .voteSelection(voteSelectionToString(voteSelection))
                .voteDeadLine(changeIntegerToTime(voteDeadLine))
                .build();
    }

    private String voteSelectionToString(List<String> vote_selection){
        StringBuilder sb = new StringBuilder();
        for(String select : vote_selection){
            sb.append(select);
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    private Date changeIntegerToTime(Integer timeAdd){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, timeAdd);
        return cal.getTime();
    }
}
