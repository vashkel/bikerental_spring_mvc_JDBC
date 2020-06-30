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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("user")

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

   @GetMapping("/user")
   public String userPage(@SessionAttribute User user, HttpServletRequest request, Model model){

        if (User.UserRoleEnum.USER.equals(user.getRole())) {
          try {
              Order openOrder = orderService.findOpenOrder(user);
              if (openOrder != null) {
                  openOrder.setBikes(bikeService.getBikesByOpenOrder(openOrder.getId()));
//                  request.getSession().setAttribute(SessionParameter.ORDER.parameter(), openOrder);
                  model.addAttribute(SessionParameter.ORDER.parameter(), openOrder);
                  RequestTimeParameter.addParam(request, openOrder.getStart_date());
              }
          }catch (ServiceException e){
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
           }catch (ServiceException e){

           }
       }
//        Order order;
//       try {
//           order = orderService.findOpenOrder(user);
//           if (order != null) {
//               order.setBikes(bikeService.getBikesByOpenOrder(order.getId()));
//               request.getSession().setAttribute(SessionParameter.ORDER.parameter(), order);
//               RequestTimeParameter.addParam(request, order.getStart_date());
//           }else {
//               request.getSession().removeAttribute("order");
//           }
//       } catch (ServiceException e) {
//           e.printStackTrace();
//       }
       return user.getRole().getHomePage();
   }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }

    @GetMapping("/")
    public String loginPage(Model model) {
        model.addAttribute("userLogin", new User());
        return "user/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("userLogin", new User());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("userLogin") User userLogin, Errors errors, HttpServletRequest request, Model model) throws ServiceException {
        if (errors.hasErrors()) {
            return "user/login";
        }
        User user;
        try {
            user = userService.login(userLogin.getLogin(), userLogin.getPassword());
            if (user == null) {
                request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.LOGIN_PASSWORD.message());
                return "user/login";
            }
//            if (User.UserRoleEnum.USER.equals(user.getRole())) {
//                Order order = orderService.findOpenOrder(user);
//                if (order != null) {
//                    order.setBikes(bikeService.getBikesByOpenOrder(order.getId()));
//                    request.getSession().setAttribute(SessionParameter.ORDER.parameter(), order);
//                    RequestTimeParameter.addParam(request, order.getStart_date());
//                }
//            }
//            List<RentalPoint> rentalPointList;
//            List<BikeType> bikeTypesList;
//            if (User.UserRoleEnum.ADMIN.equals(user.getRole())) {
//                rentalPointList = rentalPointService.getRentalPoints();
//                bikeTypesList = bikeTypeService.getBikeTypes();
//                request.getSession().setAttribute(RequestParameter.RENTAL_POINT_LIST.parameter(), rentalPointList);
//                request.getSession().setAttribute(RequestParameter.BIKE_TYPES_LIST.parameter(), bikeTypesList);
//                model.addAttribute(rentalPointList);
//                model.addAttribute(bikeTypesList);
//                model.addAttribute("bike", new Bike());
//            }
            authorization.setAuthorized(Boolean.TRUE);
            model.addAttribute(SessionParameter.USER.parameter(), user);
//            request.getSession().setAttribute(SessionParameter.USER.parameter(), user);
        } catch (ServiceException e) {
            LOGGER.error("An exception occurred while get user data : ", e);
            request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.LOGIN_PASSWORD.message());
            return "redirect:/error ";
        }
    return "redirect:/user";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) throws ServiceException {
         model.addAttribute("usersList", userService.getAllUsers());
        return "user/users";
    }

    @GetMapping("/registration")
    public String registration(Model model){
         model.addAttribute("newUser", new User());
        return "user/registration";
    }
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("newUser") User newUser,Errors errors, Model model, HttpServletRequest request) throws ServiceException {
        if(errors.hasErrors()){
            return "user/registration";
        }
        boolean isRegistered = userService.register(newUser);
        if (!isRegistered) {
            request.setAttribute(SessionParameter.MESSAGE.parameter(), PageMessage.USER_ALREADY_EXIST.message());
            return "redirect:/registration";
        }
        request.setAttribute(SessionParameter.MESSAGE.parameter(), PageMessage.USER_ADDED.message());
        model.addAttribute("userLogin", new User());
        return "user/login";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user")User user){
//        userService.register(user)
        return "redirect:/users";
    }

}
