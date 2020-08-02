package com.example.bikerental.util;

public class PageConstant {

    public static final String LOGIN_PAGE = "user/login";
    public static final String ADMIN_PAGE = "user/admin";
    public static final String USER_PAGE = "user/user";
    public static final String REGISTRATION_PAGE = "user/registration";
    public static final String USERS_PAGE = "user/users";

    public static final String ERROR_PAGE = "error/error";
    public static final String BIKES_PAGE = "bike/bikes";

    public static final String CREATE_ORDER_PAGE = "order/createBikeOrder";
    public static final String ALL_ORDERS_PAGE = "order/allOrders";
    public static final String USER_ORDERS_PAGE = "order/userOrders";

    public static final String RENTAL_POINTS_PAGE = "rentalPoint/rentalPoints";
    public static final String CONTACTS_PAGE = "rentalPoint/contacts";

    public static final String REDIRECT_TO_RENTAL_POINTS_CATALOG_PAGE = "/BikeRentalServlet?command=rental_points_page";
    public static final String REDIRECT_TO_LOGIN_PAGE = "//BikeRentalServlet?command=login";
    public static final String REDIRECT_TO_BIKE_CATALOG_PAGE = "/BikeRentalServlet?command=bikecatalog";
    public static final String REDIRECT_TO_USER_CATALOG_PAGE = "/BikeRentalServlet?command=get_all_user";
    public static final String REDIRECT_CREATE_ORDER_PAGE = "/BikeRentalServlet?command=create_order";


}