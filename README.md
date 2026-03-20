# CMS REST API
### 1. 프로젝트 실행 방법
```
git clone https://github.com/JJungH0/simple-cms-api.git
cd simple-cms-api
./gradlew bootRun
```
-   **또는 IntelliJ에서 실행**

### 2. API 테스트
- [Postman API 문서 바로가기](https://documenter.getpostman.com/view/49786142/2sBXihrYsz)
- **Postman을 이용하여 API 요청 테스트**
- **IntelliJ의 ``http/request.http`` 파일을 통해 테스트 가능**

### 3. 기본 접속 주소
`http://localhost:8080`
- H2 데이터베이스를 사용하며 별도의 설정 없이 실행 가능합니다.
---
### 인증 방식
- `Spring Security`의 `formLogin` 방식을 사용하여 인증을 처리합니다.
- 로그인 요청 시 `UserDetailsService`를 통해 사용자 정보를 조회합니다.
- 입력된 비밀번호는 `BCrypt` 알고리즘을 사용하여 저장된 값과 동일한 방식으로 해싱 후 비교하여 검증합니다.
- 인증이 성공하면 서버에 세션이 생성되고, 클라이언트에 `JSESSIONID` 쿠키가 발급됩니다.
- 이후 요청은 해당 세션을 기반으로 인증 상태가 유지됩니다.
- 로그아웃 시 세션을 무효화하여 인증 정보를 제거합니다.
---
### 구현 내용

#### **[콘텐츠 CRUD]**
- 콘텐츠 추가
- 콘텐츠 목록 조회 (페이징 처리)
- 콘텐츠 상세 조회
- 콘텐츠 수정
- 콘텐츠 삭제
#### **[로그인 기능]**
- Spring Security 기반 로그인 기능 구현
- Role 기반 권한 처리 (USER, ADMIN)
#### **[접근 권한]**
- 콘텐츠 작성자만 수정 및 삭제 가능
- 관리자(ADMIN)는 모든 콘텐츠 수정 및 삭제 가능
---
### 추가 구현 기능

#### **[회원 기능]**
- 회원 가입 기능 구현
- 로그인 사용자 정보 조회 API
- 로그아웃 기능 (세션 무효화)
#### **[콘텐츠 기능]**
- 사용자 기준 콘텐츠 조회 (memberName)
- 조회수 기준 정렬 가능
#### **[공통 기능]**
- 기본 페이지 크기 10으로 설정
- 비밀번호 BCrypt 암호화 적용
- 유효성 검증 (@Valid)
#### **[설계 및 구현 특징]**
- DTO를 활용하여 계층 간 역할을 분리
- `@Transactional`을 통한 트랜잭션 관리
- 전역 예외 처리를 통해 일관된 에러 응답 구조 유지
- 계층형 구조 (Controller - Service - Repository) 적용
---

### 사용한 AI 도구 / 참고 자료
- ChatGPT를 활용하여 API 설계 방향, Spring Security, JPA 및 인증 방식(Session 기반)에 대한 이해를 보완했습니다.
- 개인 Notion에 정리해둔 내용을 참고하여 예외 처리 구조와 공통 응답 형식을 구성했습니다.
