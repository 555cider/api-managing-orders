

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
// BeanCreationException: ~.
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

### 3.
```kotlin
dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}") {
        exclude(group = "com.vaadin.external.google", module = "android-json")
        exclude(group = "junit", module = "junit")
    }
    // exclude 추가
    // 관련 링크 참고. (https://tomgregory.com/how-to-exclude-gradle-dependencies/)
}
```
### 4. plugins에서 버전 선언 시도 → 실패
```kotlin
plugins {
    ~
}
val springBootVersion = "2.4.1"
val lombokVersion = "1.18.24"
```
```kotlin
plugins {
    ~
    id("org.springframework.boot") version "2.4.1"
    id("org.projectlombok") version "1.18.24"
}
// plugins에서 group 버전 값을 선언해 줄 수 있을까 싶어 수정했으나, 바로 오류 발생.
```
```kotlin
plugins {
    ~
}
val springBootVersion = "2.4.1"
val lombokVersion = "1.18.24"
// 원상 복귀
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

### 3. @NoArgsConstructor
```java
    // org.springframework.orm.jpa.JpaSystemException:
    // No default constructor for entity:
    // nested exception is org.hibernate.InstantiationException:
```
```java
    @NoArgsConstructor
```

### 4.
```java
	@GeneratedValue
	private Long seq;
    // InvalidDataAccessResourceUsageException
    // SQL Error: 90036, SQLState: 90036
    // Sequence "HIBERNATE_SEQUENCE" not found;
```
```java
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
    // 별도의 Sequence가 아니라 AutoIncrement인 경우에 해당
    // 관련 링크 참고. (https://velog.io/@gillog/JPA-%EA%B8%B0%EB%B3%B8-%ED%82%A4-%EC%83%9D%EC%84%B1-%EC%A0%84%EB%9E%B5IDENTITY-SEQUENCE-TABLE)
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
// InvalidDataAccessApiUsageException
```
```java
@Modifying(clearAutomatically = true)
@Query(value = "UPDATE users SET review_count = review_count + 1 WHERE seq = :seq", nativeQuery = true)
int updateReviewCountBySeq(@Param("seq") Long seq);
// InvalidDataAccessApiUsageException
```
```java
@Modifying(clearAutomatically = true)
@Query(value = "UPDATE users SET review_count = review_count + 1 WHERE seq = :seq", nativeQuery = true)
int updateReviewCountBySeq(@Param("seq") Long seq);
// InvalidDataAccessApiUsageException
// clearAutomatically, flushAutomatically 에 대해선 링크 참고. (https://freedeveloper.tistory.com/154)
```
```java
@Modifying(clearAutomatically = true)
@Query(value = "UPDATE User SET review_count = review_count + 1 WHERE seq = :seq")
int updateReviewCountBySeq(@Param("seq") Long seq);
// UPDATE 뒤에는 테이블 명이 아니라 Entity 클래스 명이 위치해야 했다. 대소문자도 구분.
// 혹은 @Entity에 name을 테이블 명으로 설정해주면 된다.
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
// Column "REJECTED" not found; SQL statement: ~.
```
```java
@Modifying
@Query(value = "UPDATE orders SET state = 'REJECTED', reject_msg = :rejectMsg, rejected_At = now() WHERE seq = :orderSeq", nativeQuery = true)
int reject(@Param("orderSeq") Long orderSeq, @Param("rejectMsg") String rejectMsg);
// 문자열은 ''로 감싸기
```

### 4.
```java
    // Field userRepository in com.github.prgrms.users.UserServiceImpl required a bean of type 'com.github.prgrms.users.UserRepository' that could not be found.
```

```java
    // UnsatisfiedDependencyException: Error creating bean with name 'UserService': Unsatisfied dependency expressed through field 'userRepository';
```

### 5. save 메소드에 맞는 파라미터(entity)로
```java
    Long reviewSeq = reviewRepository.review(userSeq, order.getProductSeq(), content);
```
```java
    Review review = new Review(new User(userSeq), new Product(order.get().getProduct().getSeq()), content);
	review = reviewRepository.save(review);
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

# Controller 이력
### 1. Paging 기본값 설정
```java
	public ApiResult<List<OrderDto>> findAll(~, Pageable pageable) { ~ }
```
```java
	public ApiResult<List<OrderDto>> findAll(~, @PageableDefault(size = 5) Pageable pageable) { ~ }
```
```java
    productService.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Order.desc("seq"))));
    // test에서 역순을 원해서 정렬 넣음
```

<br>
<br>
<br>

# 기타
### 1. JpaConfigure 추가
```java
// org.springframework.context.ApplicationContextException
// No property 'update' found for type 'User'
```

### 2. class 파일 삭제 후 실행 불가
```java
// 기본 클래스 com.github.prgrms.Application을(를) 찾거나 로드할 수 없습니다. ~ java.lang.ClassNotFoundException: com.github.prgrms.Application
// 해결 방법을 찾아보니 Eclipse 쪽 정보만 많이 나왔다. 그럼에도 고집 부리며 VSCode로 해결하려다, 결국 Eclipse로 해결. 같은 소스인데 이러는 걸 보면, VSCode나 Eclipse 등의 각 IDE는 (실행 여부에 영향을 줄 만큼 중요한) 실행 환경 파일이 별도로 존재하는 듯하다.
// 이게 과연 해결인 것인지는 모르겠는데, Eclipse에서는 그냥 실행되었던 것으로 기억한다.
```

### 3. application.yml 설정
```yml
# H2 DB 설정은 링크 참고. (http://www.h2database.com/html/features.html)
# JPA 설정은 링크 참고. (https://docs.spring.io/spring-boot/docs/2.1.x/reference/html/howto-database-initialization.html)
```

### 4. H2 DB에서 컬럼의 default 값 설정 관련 시도 중 극히 일부
```yml
    spring:
        datasource:
            schema: classpath:schema-h2.sql
            data: classpath:data-h2.sql
    # 1) resource 내의 schema.sql, data.sql을 기본으로 인식
    # 2) schema-${platform}.sql, data-${platform}.sql
    # 3) spring.datasource.schema, ~.data 에서 지정한 sql
    # 실패
```
```kotlin
    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}") {
        exclude(group = "com.vaadin.external.google", module = "android-json")
        exclude(group = "junit", module = "junit")
    }
    // 실패. 혹시 Maven의 의존성 설정을 그대로 옮기지 않아서 그런 것인가 의심했지만, 관련 없었다.
```
```java
	@Builder.Default()
	private LocalDateTime createAt = LocalDateTime.now();
    // 실패: 하다하다 entity 쪽까지 손대고 있었다. DB 생성할 때, enity에 설정된 값들을 읽어들이지 않을까 희망 회로 돌리면서.
```
```java
    @PrePersist
	public void prePersist() {
		this.createAt = LocalDateTime.now();
	}
    // 실패: 동일.
```
```yml
    spring:
        jpa:
            hibernate:
                ddl-auto: none
    # 성공: 관련 링크 참고. (https://docs.spring.io/spring-boot/docs/2.1.x/reference/html/howto-database-initialization.html)
    # 원인은 1) gradle, 2) jpa, 3) 기타 가운데 2) jpa 였다. Hibernate가 자동으로 스키마를 생성하게 할지, 직접 sql로 생성할지에 대한 설정이 필요했다.
```