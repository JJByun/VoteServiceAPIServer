# 실행 방법

```bash
# 압축 해제 후 root 디렉토리에서 이동
$ cd /build/libs
$ java -jar danngnVote.jar
```

# 투표 생성 API

설명: 사용자가 요청한 형태로 투표를 생성하는 API

Method Type: **POST**

Path: localhost:8080/vote/create

![image-20210927000812823](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927000812823.png)

![image-20210927000858252](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927000858252.png)

![image-20210927000909731](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927000909731.png)

![image-20210927000921232](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927000921232.png)

예시

```bash
POST /vote/create HTTP/1.1
Host: localhost:8080
x-user-id: id11
Content-Type: application/json
Content-Length: 173

{
    "articleId" : 123456789,
    "voteTitle" : "투표 이름2",
    "voteDescription" : "저녁 메뉴 고르기2",
    "voteSelection" : ["고기", "해산물", "샐러드", "랍스타"],
    "voteDeadLine" : 24
}
```

정상 응답

```json
{
    "voteId": "xCQ8HVUaVu"
}
```

---

# 투표 조회 API

설명: 투표 ID를 기준으로 해당 투표 현재 정보를 조회하는 API

Method Type: **GET**

Path: localhost:8080/vote/query/{userId}/{voteId}

- userId: 조회하는 사용자의 ID
- voteId: 조회하려는 투표 ID

![image-20210927000828845](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927000828845.png)

![image-20210927001006823](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927001006823.png)

![image-20210927000926408](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927000926408.png)

예시

```bash
GET /vote/query/12345678/xCQ8HVUaVu HTTP/1.1
Host: localhost:8080
x-user-id: id12
```

정상 응답

```json
{
    "userId": "id12",
    "voteTitle": "투표 이름2",
    "voteDescription": "저녁 메뉴 고르기2",
    "voteSelection": [
        "고기",
        "해산물",
        "샐러드",
        "랍스타"
    ],
    "voteDeadLine": "2021-09-27T02:12:57.584+00:00",
    "voteSelectCount": {
        "랍스타": 0,
        "고기": 0,
        "해산물": 0,
        "샐러드": 1
    },
    "userSelect": true
}
```

---

# 생성 투표 조회 API

설명: User가 생성한 모든 투표를 조회하는 API

Method Type: **GET**

Path: localhost:8080/vote/query/userMake

![image-20210927000836564](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927000836564.png)

![image-20210927001020149](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927001020149.png)

![image-20210927000932858](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927000932858.png)

예시

```bash
GET /vote/query/userMake HTTP/1.1
Host: localhost:8080
x-user-id: id11
```

정상 응답

```json
{
    "userMakeVoteList": {
        "투표 이름1": "저녁 메뉴 고르기1",
        "투표 이름2": "저녁 메뉴 고르기2"
    },
    "userId": "id11"
}
```

---

# 투표 선택 API

설명: 투표 id의 특정 투표 항목을 선택하는 API

Method Type: **PUT**

Path: localhost:8080/vote/select

![image-20210927000841805](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927000841805.png)

![image-20210927001034957](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927001034957.png)

![image-20210927001047539](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927001047539.png)

![image-20210927000936532](/Users/jongjinbyun/Library/Application Support/typora-user-images/image-20210927000936532.png)

예시

```bash
PUT /vote/select HTTP/1.1
Host: localhost:8080
x-user-id: id12
Content-Type: application/json
Content-Length: 88

{
    "articleId" : 12345678,
    "voteId" : "xCQ8HVUaVu",
    "voteSelection" : "샐러드"
}
```

정상 응답

```json
{
    "success": true
}
```