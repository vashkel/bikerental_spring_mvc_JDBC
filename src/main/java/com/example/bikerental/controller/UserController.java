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
import com.example.bikerental.util.Authorization;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("order")


public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private UserService userService;
    private OrderService orderService;
    private RentalPointService rentalPointService;
    private BikeTypeService bikeTypeService;
    private BikeService bikeService;
    private Authorization authorization;

    @Autowired
    public UserController(UserService userService, OrderService orderService, RentalPointService rentalPointService, BikeTypeService bikeTypeService, BikeService bikeService, Authorization authorization) {
        this.userService = userService;
        this.orderService = orderService;
        this.rentalPointService = rentalPointService;
        this.bikeTypeService = bikeTypeService;
        this.bikeService = bikeService;
        this.authorization = authorization;
    }

    @GetMapping("/")
    public String loginPage(Model model) {
        model.addAttribute("userLogin", new User());
        return PageConstant.LOGIN_PAGE;
    }

    @GetMapping("/user")
    public String userPage(@SessionAttribute User user, HttpServletRequest request, Model model) {

        if (User.UserRoleEnum.USER.equals(user.getRole())) {
            try {
                Order order = orderService.findOpenOrder(user);
                if (order != null) {
                    order.setBikes(bikeService.getBikesByOpenOrder(order.getId()));
                    model.addAttribute(SessionParameter.ORDER.parameter(), order);
                    RequestTimeParameter.addParam(request, order.getStart_date());
                }
            } catch (ServiceException e) {
            }
        }
        if (User.UserRoleEnum.ADMIN.equals(user.getRole())) {
            List<RentalPoint> rentalPointList;
            List<BikeType> bikeTypesList;
            try {
                rentalPointList = rentalPointService.getRentalPoints();
                bikeTypesList = bikeTypeService.getBikeTypes();
                request.getSession().setAttribute(RequestParameter.RENTAL_POINT_LIST.parameter(), rentalPointList);
                request.getSession().setAttribute(RequestParameter.BIKE_TYPES_LIST.parameter(), bikeTypesList);
                model.addAttribute(rentalPointList);
                model.addAttribute(bikeTypesList);
                model.addAttribute("bike", new Bike());
            } catch (ServiceException e) {

            }
        }
        return user.getRole().getHomePage();
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("userLogin", new User());
        return PageConstant.LOGIN_PAGE;
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("userLogin") User userLogin, Errors errors, HttpServletRequest request, Model model) throws ServiceException {
        if (errors.hasErrors()) {
            return PageConstant.LOGIN_PAGE;
        }
        User user;
        try {
            user = userService.login(userLogin.getLogin(), userLogin.getPassword());
            if (user == null) {
                request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.LOGIN_PASSWORD.message());
                return PageConstant.LOGIN_PAGE;
            }
            authorization.setAuthorized(Boolean.TRUE);
            model.addAttribute(SessionParameter.USER.parameter(), user);
            request.getSession().setAttribute(SessionParameter.USER.parameter(), user);
        } catch (ServiceException e) {
            LOGGER.error("An exception occurred while get user data : ", e);
            request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.LOGIN_PASSWORD.message());
            return "redirect:/error ";
        }
        return "redirect:/user";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) throws ServiceException {
        model.addAttribute("usersList", userService.getAllUsers());
        return PageConstant.USERS_PAGE;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("newUser", new User());
        return PageConstant.REGISTRATION_PAGE;
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("newUser") User newUser, Errors errors, Model model, HttpServletRequest request) throws ServiceException {
        if (errors.hasErrors()) {
            return PageConstant.REGISTRATION_PAGE;
        }
        boolean isRegistered = userService.register(newUser);
        if (!isRegistered) {
            request.setAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.USER_ALREADY_EXIST.message());
            return "redirect:/registration";
        }
        request.setAttribute(SessionParameter.MESSAGE.parameter(), PageMessage.USER_ADDED.message());
        model.addAttribute("userLogin", new User());
        return PageConstant.LOGIN_PAGE;
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id, HttpServletRequest request) {
        try {
            userService.deleteUserById(id);
            request.setAttribute(SessionParameter.MESSAGE.parameter(), PageMessage.USER_DELETED.message());
        } catch (ServiceException e) {
            LOGGER.error("An error occurred while the user was deleting, " + e.getMessage());
            return "error/error";
        }
        return "redirect:/users";
    }

    @PostMapping("changeUserPassword")
    public String changeUserPassword(Model model){
        User user = (User) model.getAttribute("user");

        String currentPassword = (String) model.getAttribute(RequestParameter.CURRENT_PASSWORD.parameter());
        String password = (String) model.getAttribute(RequestParameter.PASSWORD.parameter());

        try {
//            Order order = orderService.findOpenOrder(user);
//            request.setAttribute(RequestParameter.ORDER.parameter(), order);
//            if (order != null) {
//                AddTimeParameterToRequest.addParam(request, order.getStartDate());
//            }
            userService.changePassword(currentPassword, password, user);
            model.addAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.PASSWORD_CHANGED.message());
            model.addAttribute(RequestParameter.LOGIN_MENU.parameter(), false);
        } catch (ServiceException e) {
            model.addAttribute(RequestParameter.ERROR.parameter(), e);
            model.addAttribute(RequestParameter.LOGIN_MENU.parameter(), false);
        }
        return user.getRole().getHomePage();
    }

    @GetMapping("/changeState")
    public String changeState(@RequestParam("id") String id,
                              @RequestParam("userState") String userState,
                              HttpServletRequest request) {
        try {
            userService.changeStateById(id, userState);
            request.setAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.USER_STATE_CHANGED.message());

        } catch (ServiceException e) {
            LOGGER.error("An error occurred while the user was changing state , " + e.getMessage());
            request.setAttribute(RequestParameter.ERROR.parameter(), e.toString());
            return "error/error";
        }
        return "redirect:/users";
    }


    @GetMapping("/error")
    public String errorPage() {
        return "error/error";
    }


}
