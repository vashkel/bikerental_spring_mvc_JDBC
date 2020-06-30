package com.example.bikerental.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order extends Entity{
    private static final long serialVersionUID = -4434253064260999533L;
    private List<Bike> bikes = new ArrayList<>();
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private double sum;
    private User user;
//    private Bike bike;
    private OrderStatusEnum status;
    public void addBike(Bike bike){
        bikes.add(bike);
    }

}
