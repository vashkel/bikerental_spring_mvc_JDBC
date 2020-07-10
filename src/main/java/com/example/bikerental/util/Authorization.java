package com.example.bikerental.util;

import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Scope(value = "session")
@Lazy(value = false)
@Data
public class Authorization {

    private Boolean authorized = false;

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }
}
