package com.github.prgrms.users;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.prgrms.security.Jwt;

public class User {

	private final Long seq;
	private final String name;
	private final Email email;
	private final String passwd;
	private int loginCount;
	private LocalDateTime lastLoginAt;
	private final LocalDateTime createAt;

	public User(String name, Email email, String passwd) {
		this(null, name, email, passwd, 0, null, null);
	}

	public User(Long seq, String name, Email email, String passwd, int loginCount, LocalDateTime lastLoginAt,
			LocalDateTime createAt) {
		checkArgument(isNotEmpty(name), "name must be provided");
		checkArgument((name.length() >= 1) && (name.length() <= 10), "name length must be between 1 and 10");
		checkNotNull(email, "email must be provided");
		checkNotNull(passwd, "password must be provided");

		this.seq = seq;
		this.name = name;
		this.email = email;
		this.passwd = passwd;
		this.loginCount = loginCount;
		this.lastLoginAt = lastLoginAt;
		this.createAt = defaultIfNull(createAt, now());
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
		this.lastLoginAt = now();
	}

	public Long getSeq() {
		return this.seq;
	}

	public String getName() {
		return this.name;
	}

	public Email getEmail() {
		return this.email;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public int getLoginCount() {
		return this.loginCount;
	}

	public Optional<LocalDateTime> getLastLoginAt() {
		return ofNullable(this.lastLoginAt);
	}

	public LocalDateTime getCreateAt() {
		return this.createAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(this.seq, user.seq);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.seq);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("seq", this.seq)
				.append("name", this.name)
				.append("email", this.email)
				.append("passwd", "[PROTECTED]")
				.append("loginCount", this.loginCount)
				.append("lastLoginAt", this.lastLoginAt)
				.append("createAt", this.createAt)
				.toString();
	}

	static public class Builder {
		private Long seq;
		private String name;
		private Email email;
		private String passwd;
		private int loginCount;
		private LocalDateTime lastLoginAt;
		private LocalDateTime createAt;

		public Builder() {
		}

		public Builder(User user) {
			this.seq = user.seq;
			this.name = user.name;
			this.email = user.email;
			this.passwd = user.passwd;
			this.loginCount = user.loginCount;
			this.lastLoginAt = user.lastLoginAt;
			this.createAt = user.createAt;
		}

		public Builder seq(Long seq) {
			this.seq = seq;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder email(Email email) {
			this.email = email;
			return this;
		}

		public Builder passwd(String passwd) {
			this.passwd = passwd;
			return this;
		}

		public Builder loginCount(int loginCount) {
			this.loginCount = loginCount;
			return this;
		}

		public Builder lastLoginAt(LocalDateTime lastLoginAt) {
			this.lastLoginAt = lastLoginAt;
			return this;
		}

		public Builder createAt(LocalDateTime createAt) {
			this.createAt = createAt;
			return this;
		}

		public User build() {
			return new User(this.seq, this.name, this.email, this.passwd, this.loginCount, this.lastLoginAt,
					this.createAt);
		}
	}

}
