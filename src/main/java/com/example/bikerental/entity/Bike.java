package com.example.bikerental.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Bike extends Entity {
    private static final long serialVersionUID = -7055397869971477475L;
    @NotBlank(message = "Name is requared")
    private String brand;
    @NotBlank(message = "Model is requared")
    private String model;
    @NotBlank(message = "BikeType is requared")
    private BikeType bikeType;
    @NotBlank(message = "RentalPoint is requared")
    private RentalPoint rentalPoint;

    private BikeStatusEnum bikeStatus;

}
