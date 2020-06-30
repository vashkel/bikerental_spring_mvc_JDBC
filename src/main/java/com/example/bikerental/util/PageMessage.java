package com.example.bikerental.util;

public enum PageMessage {

    RENTAL_POINT_ADDED("rentalPointAdded"),
    PARKING_CHANGED("parkingChanged"),
    BIKE_ADDED("bikeAdded"),
    PROFILE_CHANGED("profileChanged"),
    PASSWORD_CHANGED("passwordChanged"),
    USER_ADDED("userAdded"),
    USER_DELETED("userDeleted"),
    ORDER_CLOSE("orderClose"),
    NOT_ENOUGH_MONEY("notEnoughMoney"),
    USER_ALREADY_EXIST("userAlreadyExist"),
    BIKE_STATUS_CHANGED("bikeStatusChanged"),
    BIKE_DELETED("bikeDeleted"),
    USER_STATE_CHANGED("userStateChanged");

    private String message;

    PageMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

}
