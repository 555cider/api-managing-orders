

# Dependency 이력
### 1. Version 불일치
```kotlin
dependencies {
    ~
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.1") // JPA
    compileOnly("org.projectlombok:lombok:1.18.24") // Lombok
    ~
}
// The following method did not exist: ~
// Correct the classpath of your application so that it contains compatible versions of ~.
```
```kotlin
dependencies {
    ~
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.4.1") // JPA
    compileOnly("org.projectlombok:lombok:1.18.24") // Lombok
    ~
}
<!-- 2.4.1.로 버전 통일 -->
```

### 2.
```kotlin
dependencies {
    ~
}
// UnsatisfiedDependencyException: ~.
//  BeanCreationException: ~.
// ClassNotFoundException: javax.xml.bind.JAXBException
```
```kotlin
dependencies {
    ~
    implementation("javax.xml.bind:jaxb-api:2.3.0")
}
// Java 9+ 인 경우에는 불필요
// 관련 링크 참고. (https://stackoverflow.com/questions/43574426/how-to-resolve-java-lang-noclassdeffounderror-javax-xml-bind-jaxbexception)
```

<br>
<br>
<br>

# Entity 이력
### 1. @JoinColumn
```java
@ManyToOne
@JoinColumn(name = "user_seq")
private User userSeq;
// 
```
```java
@ManyToOne
@JoinColumn(name = "user_seq", referencedColumnName = "seq")
private Long userSeq;
// 
```
```java
@ManyToOne
@JoinColumn(name = "user_seq", referencedColumnName = "seq")
private User userEntity;
```
### 2. @Temporal
```java
@Temporal(TemporalType.TIMESTAMP)
private LocalDateTime createAt;
// @Temporal should only be set on a java.util.Date or java.util.Calendar property
```
```java
private LocalDateTime createAt;
```

<br>
<br>
<br>

# Repository 이력
### 1.
```java
Order findOne(Long seq);
// No property 'findOne' found for type 'Order'
```
```java
Optional<Order> findById(Long seq);
// Spring Boot 2.x 부터 findOne에서 findById로 명칭이 바뀌고, Optional<>로 감싸 리턴되는 것으로 바뀌었다고 함.
```
```java
// 기본 제공이라 생략해도 무방하여 삭제
```

### 2.
```java
int update(Long seq);
// No property 'update' found for type 'User'
```
```java
int updateReviewCountBySeq(Long seq);
// No property 'updateReviewCountBySeq' found for type 'User'
```
```java
@Modifying
@Query(value = "UPDATE users SET review_count = review_count + 1 WHERE seq = ?")
int updateReviewCountBySeq(Long seq);
// JDBC style parameters (?) are not supported for JPA queries.
```
```java
@Modifying
@Query(value = "UPDATE users SET review_count = review_count + 1 WHERE seq = :seq")
int updateReviewCountBySeq(@Param("seq") Long seq);
// Validation failed for query for method ~.
// users is not mapped.
```
```java
@Modifying
@Query(value = "UPDATE users SET review_count = review_count + 1 WHERE seq = :seq", nativeQuery = true)
int updateReviewCountBySeq(@Param("seq") Long seq);
```

### 3.
```java
@Modifying
@Query(value = "UPDATE orders SET state = REJECTED, reject_msg = :rejectMsg, rejected_At = now() WHERE seq = :orderSeq", nativeQuery = true)
int reject(@Param("orderSeq") Long orderSeq, String rejectMsg);
// IllegalArgumentException: Either use @Param on all parameters except Pageable and Sort typed once, or none at all
```
```java
@Modifying
@Query(value = "UPDATE orders SET state = REJECTED, reject_msg = :rejectMsg, rejected_At = now() WHERE seq = :orderSeq", nativeQuery = true)
int reject(@Param("orderSeq") Long orderSeq, @Param("rejectMsg") String rejectMsg);
```

### 4.
```java
// Field userRepository in com.github.prgrms.users.UserServiceImpl required a bean of type 'com.github.prgrms.users.UserRepository' that could not be found.
```

```java
// UnsatisfiedDependencyException: Error creating bean with name 'UserService': Unsatisfied dependency expressed through field 'userRepository';
```

<br>
<br>
<br>

# Service 이력
### 1.
```java
// The dependencies of some of the beans in the application context form a cycle: ~
// As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.
// Requested bean is currently in creation: Is there an unresolvable circular reference?
```
```yml
spring:
    main:
        allow-circular-references: true
```

### 2.
```java
@Autowired
private UserRepository userRepository;
```
```java
private final UserRepository userRepository;
public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
}
// 의존성 주입 방법을 생성자 주입으로 변경
// 이유는 링크 참고 (https://madplay.github.io/post/why-constructor-injection-is-better-than-field-injection)
```

### 3.
```java
```

<br>
<br>
<br>

# 기타
### 1. JpaConfigure 추가
```
// org.springframework.context.ApplicationContextException
// No property 'update' found for type 'User'
```

### 2.
```
// 오류: 기본 클래스 com.github.prgrms.Application을(를) 찾거나 로드할 수 없습니다.
// 원인: java.lang.ClassNotFoundException: com.github.prgrms.Application
```
```
// VSCODE에선 "Java: Clean Java Language Server Workspace"
```

### 3. application.yml
```yml
# H2 데이터베이스의 설정은 링크 참고. (http://www.h2database.com/html/features.html)
```