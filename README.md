# Installation
아래의 명령어를 실행하여 Docker Image 를 설치한다.
```
$ cd docker
$ docker-compose up -d
```

# Usage
아래의 명령어를 실행하여 Docker Image 를 생성 및 실행한다.
```
$ docker build --tag sprinkling-money-demo:0.0.1 .
$ docker run sprinkling-money-demo:0.0.1
```

# API Docs
## 뿌리기
- URL: /sprinkling
- METHOD: POST
- Request Header

| Name | Type | Mandatory | Description |
|---|---|:---:|---|
| X-USER-ID | long | 0 | 사용자의 고유 아이디 |
| X-ROOM-ID | String | 0 | 채널의 고유 아이디 |

- Request Body

| Name | Type | Mandatory | Description |
|---|---|:---:|---|
| amount | double | O | 금액 |
| memberCnt | int | O | 인원 수 |
| currencyType | String | X | 통화 유형(USD, KRW) |

- Response Body

| Name | Type | Mandatory | Description |
|---|---|:---:|---|
| token | String | O | 뿌리기의 고유 아이디 |
| roomId | String | O | 채널의 고유 아이디 |
| amount | double | O | 금액 |
| currencyType | String | O | 통화 유형(USD, KRW) |
| createDate | long | O | 등록일자(unix timestamp) |
| expiredDate | long | O | 만료일자(unix timestamp) |
| expired | boolean | O | 만료여부 |

- Sample Request
```
curl -d '{"amount":20000.0, "memberCnt":4, "currencyType":"KRW"}' \
-H "Content-Type: application/json" \
-X POST http://localhost:8080/sprinkling
```
- Sample Response
```
{
    "token": "D9O",
    "roomId": "xsd121asa",
    "amount": 299.0,
    "currencyType": "KRW",
    "createDate": 1605862544927,
    "expiredDate": 1605895544927,
    "expired": false
}
```

## 받기
- URL: /sprinkling/{token}
- METHOD: PUT
- Request Header

| Name | Type | Mandatory | Description |
|---|---|:---:|---|
| X-USER-ID | long | O | 사용자의 고유 아이디 |
| X-ROOM-ID | String | O | 채널의 고유 아이디 |

- Request Path Variable

| Name | Type | Mandatory | Description |
|---|---|:---:|---|
| token | String | O | 뿌리기의 고유 아이디 |

- Response Body

| Name | Type | Mandatory | Description |
|---|---|:---:|---|
| userId | long | O | 사용자의 고유 아이디 |
| amount | double | O | 금액 |
| currencyType | String | O | 통화 유형(USD, KRW) |
| receivedDate | long | O | 받은일자(unix timestamp) |

- Sample Request
```
curl -H "X-USER-ID: 37283782" \
-H "X-ROOM-ID: xljB83D" \
-X PUT http://localhost:8080/sprinkling/nnj
```
- Sample Response
```
{
    "userId": 34234142,
    "amount": 138.0,
    "currencyType": "KRW",
    "receivedDate": 1605862578338
}
```

## 조회하기
- URL: /sprinkling/{token}
- METHOD: GET
- Request Header

| Name | Type | Mandatory | Description |
|---|---|:---:|---|
| X-USER-ID | long | O | 사용자의 고유 아이디 |
| X-ROOM-ID | String | O | 채널의 고유 아이디 |

- Request Path Variable

| Name | Type | Mandatory | Description |
|---|---|:---:|---|
| token | String | O | 뿌리기의 고유 아이디 |

- Response Body

| Name | Type | Mandatory | Description |
|---|---|:---:|---|
| token | String | O | 뿌리기의 고유 아이디 |
| roomId | String | O | 채널의 고유 아이디 |
| amount | double | O | 금액 |
| currencyType | String | O | 통화 유형(USD, KRW) |
| toReceivers | Array | O | 받아간 사용자 |
| createDate | long | O | 등록일자(unix timestamp) |
| expiredDate | long | O | 만료일자(unix timestamp) |
| expired | boolean | O | 만료여부 |
| receivedAmount | double | O | 총 받아간 금액 |

- Sample Request
```
curl -H "X-USER-ID: 8482827" \
-H "X-ROOM-ID: xljB83D" \
-X GET http://localhost:8080/sprinkling/nnj
```
- Sample Response
```
{
    "token": "D9O",
    "roomId": "xsd121asa",
    "amount": 299.0,
    "currencyType": "KRW",
    "toReceivers": [
        {
            "userId": 34234142,
            "amount": 138.0,
            "receivedDate": 1605862578338,
            "currencyType": "KRW"
        },
        {
            "userId": 0,
            "amount": 161.0,
            "receivedDate": 0,
            "currencyType": "KRW"
        }
    ],
    "createDate": 1605862544927,
    "expiredDate": 1605895544927,
    "expired": false,
    "receivedAmount": 138.0
}
```

# Using Library list
1. Java 11
2. Spring Boot 2.4.0
3. MongoDB
4. Redis
