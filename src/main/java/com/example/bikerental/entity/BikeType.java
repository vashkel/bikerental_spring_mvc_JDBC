package com.example.bikerental.entity;

import lombok.Data;

@Data
public class BikeType extends Entity {
    private static final long serialVersionUID = -1784150257366390793L;

    private BikeTypeEnum type;

}
