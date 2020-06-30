package com.example.bikerental.util;

public class PageConstant {

    public static final String LOGIN_PAGE = "login";
    public static final String ADMIN_PAGE = "user/admin";
    public static final String USER_PAGE = "user/user";
    public static final String REGISTRATION_PAGE = "registration";
    public static final String USER_CATALOG_PAGE = "users";
    public static final String ERROR_PAGE = "error";
    public static final String ADD_BIKE_PAGE = "/WEB-INF/jsp/bike/addBike.jsp";
    public static final String BIKE_CATALOG_PAGE = "/WEB-INF/jsp/bike/bikeCatalog.jsp";
    public static final String CREATE_ORDER_PAGE = "createBikeOrder";
    public static final String USER_ORDERS_CATALOG_PAGE = "/WEB-INF/jsp/order/orderCatalog.jsp";
    public static final String ORDER_REPORTS_PAGE = "/WEB-INF/jsp/order/ordersReport.jsp";
    public static final String RENTAL_POINTS_CATALOG_PAGE = "/WEB-INF/jsp/rentalpoint/rentalPointsCatalog.jsp";
    public static final String REDIRECT_TO_RENTAL_POINTS_CATALOG_PAGE = "/BikeRentalServlet?command=rental_points_page";
    public static final String REDIRECT_TO_LOGIN_PAGE = "//BikeRentalServlet?command=login";
    public static final String REDIRECT_TO_BIKE_CATALOG_PAGE = "/BikeRentalServlet?command=bikecatalog";
    public static final String REDIRECT_TO_USER_CATALOG_PAGE = "/BikeRentalServlet?command=get_all_user";
    public static final String REDIRECT_CREATE_ORDER_PAGE = "/BikeRentalServlet?command=create_order";


}