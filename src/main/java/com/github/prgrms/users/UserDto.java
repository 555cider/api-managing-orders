package com.github.prgrms.users;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String name;

    private Email email;

    private int loginCount;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createAt;

    public UserDto() {
    }

    public UserDto(User user) {
        copyProperties(user, this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", this.name)
                .append("email", this.email)
                .append("loginCount", this.loginCount)
                .append("lastLoginAt", this.lastLoginAt)
                .append("createAt", this.createAt)
                .toString();
    }

}