package com.github.prgrms.users;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.matches;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Email {

    private final String address;

    public Email(String address) {
        checkArgument(isNotEmpty(address), "address must be provided");
        checkArgument((address.length() >= 4) && (address.length() <= 50),
                "address length must be between 4 and 50 characters");
        checkArgument(checkAddress(address), "Invalid email address: " + address);

        this.address = address;
    }

    public static Email of(String address) {
        return new Email(address);
    }

    private static boolean checkAddress(String address) {
        return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", address);
    }

    public String getAddress() {
        return this.address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(this.address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.address);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("address", this.address).toString();
    }

}