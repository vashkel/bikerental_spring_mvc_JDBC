package com.example.bikerental.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Bike extends Entity {
    private static final long serialVersionUID = -7055397869971477475L;
    @NotNull(message = "Name is required")
    private String brand;
    @NotNull(message = "Model is required")
    private String model;
    private BikeType bikeType;
    private RentalPoint rentalPoint;

    private BikeStatusEnum bikeStatus;

}
