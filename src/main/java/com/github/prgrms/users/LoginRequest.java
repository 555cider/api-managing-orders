package com.github.prgrms.users;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LoginRequest {

    @NotBlank(message = "principal must be provided")
    private String principal;

    @NotBlank(message = "credentials must be provided")
    private String credentials;

    protected LoginRequest() {
    }

    public LoginRequest(String principal, String credentials) {
        this.principal = principal;
        this.credentials = credentials;
    }

    public String getPrincipal() {
        return this.principal;
    }

    public String getCredentials() {
        return this.credentials;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("principal", this.principal)
                .append("credentials", this.credentials)
                .toString();
    }

}