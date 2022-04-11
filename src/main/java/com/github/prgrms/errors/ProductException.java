package com.github.prgrms.errors;

public class ProductException extends RuntimeException {

    public ProductException(String message) {
	super(message);
    }

    public ProductException(String message, Throwable cause) {
	super(message, cause);
    }

}