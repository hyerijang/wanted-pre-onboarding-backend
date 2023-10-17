# 원티드 프리온보딩 백엔드 인턴십 선발 과제

## 과제 안내
> 아래 서비스 개요 및 요구 사항을 만족하는 API 서버를 구현합니다.

## 서비스 개요
> 본 서비스는 기업의 채용을 위한 웹 서비스 입니다.
> 회사는 채용공고를 생성하고, 이에 사용자는 지원합니다.

## 기술 스택
- JDK 17
- Spring Boot stater web 3.1.4
- Spring data jpa 3.1.4
- Lombok 1.18.30
- H2 2.1.214

## 요구사항 분석
0. 회사 등록
1. 채용공고 등록
2. 채용공고 수정
3. 채용공고 삭제
4. 채용공고 조회
    1. 채용공고 목록 조회
    2. 채용공고 검색 (선택)
5. 채용 상세 페이지 가져오기
    1. 해당 회사의 다른 채용공고 포함(선택)
6. 채용 공고 지원 (선택)

## 구현 과정 및 고려사항

### API URI
| 기능         | METHOD | URI                                |
|------------|--------|------------------------------------|
| 채용공고 등록    | POST   | /recruits                          |
| 채용공고 수정    | PATCH  | /recruits/{id}                     |
| 채용공고 삭제    | DELETE | /recruits/{id}                     |
| 채용공고 목록 조회 | GET    | /recruits/?page={page},size={size} |
| 채용 상세 페이지  | GET    | /recruits/{id}                     |


### 공통
- Requset, Response에 validation 적용
- DTO - Entity 변환은 서비스단에서 이루어진다.
- 단위테스트 작성 (repository, service, controller, beanValidation)

### 회사 등록
```json
{
  "name" : "원티드"
}
```
- 회사와 채용공고는 1대다 관계이다. -> 채용공고에는 회사 ID가 FK로 포함된다.
- 회사가 있어야 채용공고 등록할 수 있으므로 구현하였음.

### 채용공고 등록

```json
(requset 예시)
   {
      "position":"백엔드 주니어 개발자",
      "rewoard":1000000,
      "content":"원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
      "skills":"Python",
      "companyId": 1001
   }
```

```json
(response 예시)
{
   "id": 100,
   "position": "백엔드 주니어 개발자",
   "rewoard": 1000000,
   "content": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
   "skills": "Python",
   "companyName": "원티드"
}
```

- 응답 시 채용공고ID를 포함한다.
- 응답 시 회사ID가 아닌 회사명을 반환한다.
