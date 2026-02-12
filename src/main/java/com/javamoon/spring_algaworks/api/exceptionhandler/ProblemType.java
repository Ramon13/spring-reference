package com.javamoon.spring_algaworks.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_EXCEPTION("/business-exception", "Business rule violation"),
    UNREADABLE_MESSAGE("/unreadable-message", "Unreadable message"),
    INVALID_PARAM("/invalid-param", "Invalid param"),
    SYSTEM_ERROR("/system-error", "System error");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://api-error.com.br" + path;
        this.title = title;
    }
}
