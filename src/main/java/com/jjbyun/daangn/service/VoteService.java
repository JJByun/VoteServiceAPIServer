package com.jjbyun.daangn.service;

import com.jjbyun.daangn.dao.VoteInfoRepository;
import com.jjbyun.daangn.dao.VoteListRepository;
import com.jjbyun.daangn.dto.*;
import com.jjbyun.daangn.entity.VoteInfo;
import com.jjbyun.daangn.entity.VoteList;
import com.jjbyun.daangn.exception.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VoteService {
    @Autowired
    VoteInfoRepository voteInfoRepository;
    @Autowired
    VoteListRepository voteListRepository;
    @Autowired
    RequestException requestException;
    public CreateVoteResponseDto createVote(String userId, CreateVoteRequestDto body) {
        //body 조건 검사하기
        if(!isCreateVoteParameterValid(userId, body)) return (CreateVoteResponseDto) requestException.parameterException();

        //랜덤 vote Id 생성하기
        String voteId="";
        while(true){
            voteId = generateVoteId();
            if(!voteInfoRepository.existsById(voteId)) break;
        }

        voteInfoRepository.save(new CreateVoteRequestDto(body).toEntity(userId, voteId));
        return CreateVoteResponseDto.builder().voteId(voteId).build();
    }

    public QueryVoteResponseDto queryVote(String userId, String articleId, String voteId){
        //유효하지 않은 vote ID는 exception
        if(!voteInfoRepository.findById(voteId).isPresent()) return (QueryVoteResponseDto) requestException.parameterException();

        VoteInfo voteInfo = voteInfoRepository.findByVoteId(voteId).get();
        return QueryVoteResponseDto.builder()
                .userId(userId)
                .voteTitle(voteInfo.getVoteTitle())
                .voteDescription(voteInfo.getVoteDescription())
                .voteSelection(voteSelectionStringToList(voteInfo.getVoteSelection()))
                .voteDeadLine(voteInfo.getVoteDeadLine())
                .isUserSelect(voteListRepository.findByUserIdAndVoteId(userId, voteId).isPresent())
                .voteSelectCount(voteSelectCountToList(voteId, voteInfo.getVoteSelection()))
                .build();
    }
    public QueryUserMakeVoteResponseDto queryUserMakeVote(String userId){
        return QueryUserMakeVoteResponseDto.builder()
                .UserId(userId)
                .userMakeVoteList(userMakeVoteList(userId))
                .build();
    }

    public SelectVoteResponseDto selectVote(String userId, SelectVoteRequestDto body){
        //투표 선택하기
        if(isSelectFinished(userId, body)){
            return SelectVoteResponseDto.builder().success(true).build();
        }else{
            return SelectVoteResponseDto.builder().success(false).build();
        }
    }

    private boolean isCreateVoteParameterValid(String user_id, CreateVoteRequestDto body){
        // 0. User ID가 올바르게 들어왔는지 -> 4문자 이하 문자열
        if(!isRightUserId(user_id)) return false;
        // 1. 게시글 아이디가 적당하게 들어왔는지 -> Long Type 체크
//        if(!isRightArticleID(body.getArticle_id())) return false;
        // 2. 투표 제목이 들어왔는지 + 길이가 100 이하인지
        if(!isRightVoteTitle(body.getVoteTitle())) return false;
        // 3. 투표 최대 길이가 10000  이하인지 + 안들어와도 됨
        if(!isRightVoteDescription(body.getVoteDescription())) return false;
        // 4. 투표 항목이 각 50글자 이하인지, 2~100개 사이인지 체크
        if(!isRightVoteSelection(body.getVoteSelection())) return false;
        // 5. 마감 시간은 옵셔널 + 1~24시간 단위로 구분
        if(!isRightVoteDeadLine(body.getVoteDeadLine())) return false;

        return true;
    }

    private boolean isRightUserId(String user_id) {
        if(user_id.equals("") || user_id.length() > 4) return false;
        return true;
    }

    private boolean isRightVoteDeadLine(Integer deadLine) {
        return true;
    }

    private boolean isRightVoteSelection(List<String> voteSelection) {
        if(voteSelection.size() < 2 || voteSelection.size() > 100) return false;
        for(String vote_select : voteSelection){
            if(vote_select.length() > 50) return false;
        }
        return true;
    }

    private boolean isRightVoteDescription(String voteDescription) {
        if(!voteDescription.equals("")){
            return voteDescription.length() <= 10000;
        }
        else return true;
    }

    private boolean isRightVoteTitle(String voteTitle) {
        return !voteTitle.equals("") && voteTitle.length() <= 100;
    }

    private boolean isRightArticleID(Long article_id) {
        return article_id != null;
    }


    /*
    * 10자리 랜덤 문자열 만드는 매소드
    * 출처: https://linkeverything.github.io/java/java-random-string/
    * */
    private String generateVoteId(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /*
    * 투표 선택 성공 or 실패 return
    * */
    private boolean isSelectFinished(String userId, SelectVoteRequestDto body){
        //유효한 투표 id 체크
        if(!voteInfoRepository.existsById(body.getVoteId())) return false;
        //투표 항목이 존재하는지 체크
        if(!voteInfoRepository.findById(body.getVoteId()).get().getVoteSelection().contains(body.getVoteSelection())) return false;
        //이미 투표 했는지 체크
        if(voteListRepository.findByUserIdAndVoteId(userId, body.getVoteId()).isPresent()) return false;
        //투표 시간을 지났는지 체크
        Date curTime = new Date();
        if(curTime.after(voteInfoRepository.findById(body.getVoteId()).get().getVoteDeadLine())) return false;

        voteListRepository.save(new SelectVoteRequestDto(body).toEntity(userId));
        return true;
    }

    /*
    * 투표 항목 List로 변환
    * */
    private List<String> voteSelectionStringToList(String voteSelection) {
        String[] selection = voteSelection.split(",");
        return new ArrayList<>(Arrays.asList(selection));
    }

    /*
    * 투표 항목 별 투표 수 갖고오기
    * */
    private Map<String,Integer> voteSelectCountToList(String voteId, String voteSelection) {
        Map<String,Integer> ret = new HashMap<>();
        String[] selection = voteSelection.split(",");
        for(String select : selection){
            long count = voteListRepository.countByVoteIdAndVoteSelect(voteId,select);
            ret.put(select, (int) count);
        }
        return ret;
    }

    /*
    * 특정 User가 생성한 모든 투표 가져오기
    * */
    private Map<String, String> userMakeVoteList(String userId) {
        Map<String, String> ret = new HashMap<>();
        if(!voteInfoRepository.findFirstByUserId(userId).isPresent()) return ret;

        List<VoteInfo> voteInfos = voteInfoRepository.findAllByUserId(userId);
        for(VoteInfo voteInfo : voteInfos){
            ret.put(voteInfo.getVoteTitle(), voteInfo.getVoteDescription());
        }
        return ret;
    }
}
