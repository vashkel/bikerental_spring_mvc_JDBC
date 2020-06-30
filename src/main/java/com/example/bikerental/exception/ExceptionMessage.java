package com.example.bikerental.exception;

public enum ExceptionMessage {

    LOGIN_PASSWORD("loginOrPasswordWrong"),
    NOT_ENOUGH_MONEY("notEnoughMoney"),
    ORDER_NOT_EXIST("orderNotExist"),
    NULL_RENTAL_POINT_ID_OR_BIKE_TYPE_ID("nullRentalPointIdOrBikeTypeId"),
    VALIDATION_ERROR("validationError"),
    CURRENT_PASSW_WRONG("currentPasswordWrong"),
    NOT_FREE_BIKE("notFreeBike");
    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
