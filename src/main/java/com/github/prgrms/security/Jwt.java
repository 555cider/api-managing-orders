package com.github.prgrms.security;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public final class Jwt {

	private final String issuer;
	private final String clientSecret;
	private final int expirySeconds;
	private final Algorithm algorithm;
	private final JWTVerifier jwtVerifier;

	public Jwt(String issuer, String clientSecret, int expirySeconds) {
		this.issuer = issuer;
		this.clientSecret = clientSecret;
		this.expirySeconds = expirySeconds;
		this.algorithm = Algorithm.HMAC512(clientSecret);
		this.jwtVerifier = JWT.require(this.algorithm).withIssuer(issuer).build();
	}

	public String create(Claims claims) {
		Date now = new Date();
		JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
		builder.withIssuer(this.issuer);
		builder.withIssuedAt(now);
		if (this.expirySeconds > 0) {
			builder.withExpiresAt(new Date(now.getTime() + (this.expirySeconds * 1_000L)));
		}
		builder.withClaim("userKey", claims.userKey);
		builder.withClaim("name", claims.name);
		builder.withArrayClaim("roles", claims.roles);
		return builder.sign(this.algorithm);
	}

	public Claims verify(String token) throws JWTVerificationException {
		return new Claims(this.jwtVerifier.verify(token));
	}

	public String getIssuer() {
		return this.issuer;
	}

	public String getClientSecret() {
		return this.clientSecret;
	}

	public int getExpirySeconds() {
		return this.expirySeconds;
	}

	public Algorithm getAlgorithm() {
		return this.algorithm;
	}

	public JWTVerifier getJwtVerifier() {
		return this.jwtVerifier;
	}

	static public class Claims {
		Long userKey;
		String name;
		String[] roles;
		Date iat;
		Date exp;

		private Claims() {
			/* empty */}

		Claims(DecodedJWT decodedJWT) {
			Claim userKey = decodedJWT.getClaim("userKey");
			if (!userKey.isNull()) {
				this.userKey = userKey.asLong();
			}
			Claim name = decodedJWT.getClaim("name");
			if (!name.isNull()) {
				this.name = name.asString();
			}
			Claim roles = decodedJWT.getClaim("roles");
			if (!roles.isNull()) {
				this.roles = roles.asArray(String.class);
			}
			this.iat = decodedJWT.getIssuedAt();
			this.exp = decodedJWT.getExpiresAt();
		}

		public static Claims of(long userKey, String name, String[] roles) {
			Claims claims = new Claims();
			claims.userKey = userKey;
			claims.name = name;
			claims.roles = roles;
			return claims;
		}

		long iat() {
			return this.iat != null ? this.iat.getTime() : -1;
		}

		long exp() {
			return this.exp != null ? this.exp.getTime() : -1;
		}

		void eraseIat() {
			this.iat = null;
		}

		void eraseExp() {
			this.exp = null;
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
					.append("userKey", this.userKey)
					.append("name", this.name)
					.append("roles", Arrays.toString(this.roles))
					.append("iat", this.iat)
					.append("exp", this.exp)
					.toString();
		}
	}

}