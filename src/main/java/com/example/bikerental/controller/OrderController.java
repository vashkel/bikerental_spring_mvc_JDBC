package com.example.bikerental.controller;

import com.example.bikerental.entity.Bike;
import com.example.bikerental.entity.BikeType;
import com.example.bikerental.entity.Order;
import com.example.bikerental.entity.RentalPoint;
import com.example.bikerental.entity.User;
import com.example.bikerental.exception.ExceptionMessage;
import com.example.bikerental.exception.ServiceException;
import com.example.bikerental.service.BikeService;
import com.example.bikerental.service.BikeTypeService;
import com.example.bikerental.service.OrderService;
import com.example.bikerental.service.RentalPointService;
import com.example.bikerental.service.UserService;
import com.example.bikerental.util.PageConstant;
import com.example.bikerental.util.PageMessage;
import com.example.bikerental.util.RequestParameter;
import com.example.bikerental.util.RequestTimeParameter;
import com.example.bikerental.util.SessionParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SessionAttributes("order")
@RequestMapping(path = "/order")
public class OrderController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

    private final UserService userService;
    private final RentalPointService rentalPointService;
    private final BikeTypeService bikeTypeService;
    private final BikeService bikeService;
    private final OrderService orderService;

    @Autowired
    public OrderController(UserService userService, RentalPointService rentalPointService, BikeTypeService bikeTypeService, BikeService bikeService, OrderService orderService) {
        this.userService = userService;
        this.rentalPointService = rentalPointService;
        this.bikeTypeService = bikeTypeService;
        this.bikeService = bikeService;
        this.orderService = orderService;
    }

    @GetMapping()
    public String showOrderForm(Model model) {
        try {
            List<RentalPoint> rentalPointList = rentalPointService.getRentalPoints();
            List<BikeType> bikeTypesList = bikeTypeService.getBikeTypes();
            model.addAttribute("rentalPointList", rentalPointList);
            model.addAttribute("bikeTypeList", bikeTypesList);
            model.addAttribute("bike", new Bike());
//            if(!model.containsAttribute("order")){
//                model.addAttribute("order" , new Order());
//            }
        } catch (ServiceException e) {
            LOGGER.error("Get data error, ", e);
            return "error/error";
        }
        return PageConstant.CREATE_ORDER_PAGE;
    }

    @PostMapping("/addBikeToOrder")
    public String addBikeToOrder(@ModelAttribute("bike") Bike bike, @SessionAttribute Order order, HttpServletRequest request) throws ServiceException {
        bike = bikeService.getBikeByTypeAndRentalPointId(bike.getBikeType().getId(), bike.getRentalPoint().getId());
        if (bike == null) {
            request.setAttribute(RequestParameter.MESSAGE.parameter(), ExceptionMessage.NOT_FREE_BIKE.message());
            return "createBikeOrder";
        }
        order.addBike(bike);
        return "redirect:/order";
    }

    @PostMapping("/createOrder")
    public String createOrder(Order order, HttpServletRequest request, Model model) throws ServiceException {
        User user = (User) request.getSession().getAttribute(SessionParameter.USER.parameter());
        double userBalance = user.getBalance();
        if (userBalance < 500) {
            request.setAttribute(RequestParameter.MESSAGE.parameter(), ExceptionMessage.NOT_ENOUGH_MONEY.message());
            return PageConstant.CREATE_ORDER_PAGE;
        }
        order = orderService.createOrder(order.getBikes(), user);
        model.addAttribute("order", order);
        RequestTimeParameter.addParam(request, order.getStart_date());
        return "redirect:/user";
    }

    @PostMapping("/closeOrder")
    public String closeOrder(@SessionAttribute Order order, HttpServletRequest request, SessionStatus sessionStatus, Model model) {
        try {
            boolean isPerformed = orderService.closeOrder(order);
            if (isPerformed) {
                sessionStatus.setComplete();
                request.setAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.ORDER_CLOSE.message());
                User updatedUser = userService.getByID(order.getUser().getId());
                request.getSession().setAttribute("user", updatedUser);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while closing order,", e);
            return "redirect:/error";
        }
        return "redirect:/user";
    }

    @GetMapping("/userOrders")
    public String getUserOrders(@SessionAttribute("user") User user, Model model) throws ServiceException {
        try {
            List<Order> orderList = orderService.getAllOrderByUser(user);
            model.addAttribute("orderList", orderList);
        } catch (ServiceException e) {
            LOGGER.error("Get orders by user error : ", e);
            return "redirect:error/error";
        }
        return PageConstant.USER_ORDERS_PAGE;
    }

    @GetMapping("/allOrders")
    public String allOrders(Model model) {
        List<Order> orderListAllUsers;
        try {
            orderListAllUsers = orderService.getAllOrders();
            model.addAttribute(RequestParameter.ORDER_LIST_ALL_USERS.parameter(), orderListAllUsers);
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while find orders, ", e);
            return "error/error";
        }

        return PageConstant.ALL_ORDERS_PAGE;
    }
}
