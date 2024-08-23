package org.szy.exception;

public class BusinessException extends RuntimeException {

    private final String description;

    public BusinessException(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
