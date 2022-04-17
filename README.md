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
