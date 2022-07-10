
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
Order findBySeq(Long seq);
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

<br>
<br>
<br>

# Dependency 이력
### 1.
```kts
dependencies {
    ~
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.1") // JPA
    compileOnly("org.projectlombok:lombok:1.18.24") // Lombok
    ~
}
// The following method did not exist: ~
// Correct the classpath of your application so that it contains compatible versions of ~.
```
```kts
dependencies {
    ~
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.4.1") // JPA
    compileOnly("org.projectlombok:lombok:1.18.24") // Lombok
    ~
}
<!-- 2.4.1.로 버전 통일 -->
```
