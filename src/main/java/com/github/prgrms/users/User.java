package com.github.prgrms.users;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.prgrms.security.Jwt;
import com.google.common.base.Preconditions;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	private Long seq;

	private String name;

	private String email;

	private String passwd;

	private int loginCount;

	private LocalDateTime lastLoginAt;

	private LocalDateTime createAt;

	public User(Long seq) {
		this.seq = seq;
	}

	public User(String name, String email, String passwd) {
		this(null, name, email, passwd, 0, null, null);
	}

	public User(Long seq, String name, String email, String passwd, int loginCount, LocalDateTime lastLoginAt,
			LocalDateTime createAt) {
		Preconditions.checkArgument(ObjectUtils.isNotEmpty(name), "name must be provided");
		Preconditions.checkArgument((name.length() >= 1) && (name.length() <= 10),
				"name length must be between 1 and 10");
		Preconditions.checkNotNull(email, "email must be provided");
		Preconditions.checkNotNull(passwd, "password must be provided");
		this.seq = seq;
		this.name = name;
		this.email = email;
		this.passwd = passwd;
		this.loginCount = loginCount;
		this.lastLoginAt = lastLoginAt;
		this.createAt = createAt;
	}

	public String newJwt(Jwt jwt, String[] roles) {
		Jwt.Claims claims = Jwt.Claims.of(this.seq, this.name, roles);
		return jwt.create(claims);
	}

	public void login(PasswordEncoder passwordEncoder, String credentials) {
		if (!passwordEncoder.matches(credentials, this.passwd)) {
			throw new IllegalArgumentException("Bad credential");
		}
	}

	public void afterLoginSuccess() {
		this.loginCount++;
		this.lastLoginAt = LocalDateTime.now();
	}

}
