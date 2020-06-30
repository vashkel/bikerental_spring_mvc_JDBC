package com.example.bikerental.util;

public enum RequestParameter {
    ADDRESS("address"),
    BIKE("bike"),
    BIKE_ID("bikeId"),
    BIKE_TYPES_LIST("bikeTypesList"),
    BIKE_TYPE_ID("bikeTypeId"),
    BIKE_TYPE("bikeType"),
    BIKE_LIST("bikeList"),
    BRAND("brand"),
    BRAND_ID("brandId"),
    BRAND_LIST("brandsList"),
    EMAIL("email"),
    ERROR("error"),
    LOGIN_MENU("loginMenu"),
    LOGIN("login"),
    MESSAGE("message"),
    MODEL("model"),
    NAME("name"),
    NEXT_PAGE("next"),
    ORDER("order"),
    ORDER_ID("orderId"),
    ORDER_LIST("orderList"),
    ORDER_LIST_ALL_USERS ("orderListAllUsers"),
    PASSWORD("password"),
    PAGE_ACTION("pageAction"),
    PREVIOUS_PAGE("previous"),
    RENTAL_POINT_LIST("rentalPointList"),
    RENTAL_POINT_ID("rentalPointId"),
    SURNAME("surname"),
    USERS_LIST("usersList"),
    SECONDS("seconds"),
    MINUTES("minutes"),
    HOURS("hours"),
    DAYS("days"),
    TOTAL_PRICE("totalPrice"),
    CURRENT_PASSWORD("currentPassword"),
    BIKES_BY_TYPES("bikesByTypes"),
    BIKE_COUNT("bikeCount");
    private String parameter;

    RequestParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * @return String representation of the parameter.
     */
    public String parameter() {
        return parameter;
    }
}
