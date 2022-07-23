# [백엔드] 주문관리 API 서버 개발

출처: 프로그래머스 실력 체크 (https://programmers.co.kr/skill_check_assignments/232)

## 1.
- 리뷰 작성 후 재조회하는 부분에서 애먹었다.
- JDBCTemplate로 쿼리를 실행하면 "InvocationTargetException"이 발생하는데, 원인을 잘못 파악해 시간이 많이 소요되었다.

- 당초 원인을 "의존성 주입 관련 인스턴스 생성에서의 문제"라 판단했으나, 상관 없었다. (약간은 있었나)
- JDBCTemplate로 쿼리 실행 후, KeyHolder로 AutoIncrement된 키를 반환받고 있었다고 생각했으나 그렇지 않았다.

- PreparedStatement 생성하는 로직을 수정하며 해결했다.\
  전: JDBCTemplate의 update 메소드의 파라미터 위치에서 람다식으로 PreparedStatement를 생성\
  후: PreparedStatementCreatorFactory, PreparedStatementCreator로 따로 생성하고 ~Factory 인스턴스는 setReturnGeneratedKeys(true)

## 2.
- 주문과 리뷰를 함께 조회해도 되는 문제였지만, 가능한 한 분리하고 싶어서 따로 조회하도록 만들었다.

<br><hr><br>

## 3.
- maven을 gradle로 변경\
  : 이유 없음. gradle 테스트.
  : groovy 대신 kotlin 으로 사용.

## 4.
- JPA로 변경\
- : 이유 없음. JPA 테스트.
#### 1) 07/10... 실행만 되고, 테스트 통과는 못하는 상황 (수정 예정)
#### 2) 07/17... 서버 실행 시 스키마 쿼리만 실행되고 데이터 쿼리는 실행되지 않는 점이 문제
#### 3) 07/20... 데이터 쿼리보다도 스키마 쿼리 실행 부분이 문제의 원인인 듯. 스키마 쿼리가 실행은 되는데 default 값 등이 제대로 반영되지 않는다.
#### 4) 07/23... Maven-MyBatis 버전(master 브랜치)에서는 default 값 설정이 정상 작동
#### 5) 07/23... Eclipse에서 시도해보니 결과는 동일
#### 6) 07/23... Maven에서 Gradle로 전환한 부분이 원인일까 싶어, 의존성을 Maven 버전과 동일하게 세팅하여 시도했으나 결과는 동일
#### 7) 07/23... 해결. spring.jpa.hibernate.ddl-auto를 none으로. Hibernate가 schema를 자동적으로 생성하고 있었는데, 이것을 막아주었어야 했다.
