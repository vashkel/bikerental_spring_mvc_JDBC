package com.example.bikerental.entity;

import lombok.Data;

@Data
public class RentalPoint extends Entity {
    private static final long serialVersionUID = 8788349593812835802L;
    private String name;
    private String address;
    private String tel;



}
