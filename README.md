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

<img src="https://user-images.githubusercontent.com/13127145/135104372-d0a1a08f-6bab-47bf-9f89-40f4d336c320.png"/>

<img src="https://user-images.githubusercontent.com/13127145/135104772-a473ad28-ae15-4b17-a291-2e0c4b563d9f.png"/>

<img src="https://user-images.githubusercontent.com/13127145/135105092-ba023570-dae8-4cca-8b09-affcc759c451.png"/>

<img src ="https://user-images.githubusercontent.com/13127145/135105196-022ccef9-c6b8-4367-96b1-22c6a70737e7.png"/>

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

<img src="https://user-images.githubusercontent.com/13127145/135104372-d0a1a08f-6bab-47bf-9f89-40f4d336c320.png"/>

<img src ="https://user-images.githubusercontent.com/13127145/135105415-1b1cc9c2-1b65-47c5-9dfd-43ca0d40b60f.png"/>

<img src ="https://user-images.githubusercontent.com/13127145/135105196-022ccef9-c6b8-4367-96b1-22c6a70737e7.png"/>

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

<img src="https://user-images.githubusercontent.com/13127145/135104372-d0a1a08f-6bab-47bf-9f89-40f4d336c320.png"/>

<img src ="https://user-images.githubusercontent.com/13127145/135105530-1a1bbe1b-2987-4694-96bb-665f50be6cc6.png"/>

<img src ="https://user-images.githubusercontent.com/13127145/135105196-022ccef9-c6b8-4367-96b1-22c6a70737e7.png"/>

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

<img src="https://user-images.githubusercontent.com/13127145/135104372-d0a1a08f-6bab-47bf-9f89-40f4d336c320.png"/>

<img src ="https://user-images.githubusercontent.com/13127145/135105634-acc4bc1d-4d2c-4460-8fd2-f79447ea08cd.png"/>

<img src ="https://user-images.githubusercontent.com/13127145/135105718-45a7ea1b-0840-410b-850a-5cc35b09f05d.png"/>

<img src ="https://user-images.githubusercontent.com/13127145/135105196-022ccef9-c6b8-4367-96b1-22c6a70737e7.png"/>

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