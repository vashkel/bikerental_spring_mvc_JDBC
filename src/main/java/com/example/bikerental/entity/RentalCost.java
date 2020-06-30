package com.example.bikerental.entity;

import lombok.Data;

@Data
public class RentalCost extends Entity {
    private static final long serialVersionUID = 1169377864944323330L;

    private BikeType bikeType;
    private double price;


}
