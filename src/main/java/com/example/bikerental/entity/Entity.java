package com.example.bikerental.entity;

import java.io.Serializable;

public class Entity implements Serializable {

    private static final long serialVersionUID = -1623322101091902028L;
    private long id;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
