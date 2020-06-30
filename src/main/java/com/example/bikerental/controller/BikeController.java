package com.example.bikerental.controller;

import com.example.bikerental.entity.Bike;
import com.example.bikerental.entity.BikeStatusEnum;
import com.example.bikerental.entity.BikeType;
import com.example.bikerental.entity.RentalPoint;
import com.example.bikerental.exception.ExceptionMessage;
import com.example.bikerental.exception.ServiceException;
import com.example.bikerental.service.BikeService;
import com.example.bikerental.service.BikeTypeService;
import com.example.bikerental.service.RentalPointService;
import com.example.bikerental.util.PageMessage;
import com.example.bikerental.util.RequestParameter;
import com.example.bikerental.util.RequestParameterHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/bike")
public class BikeController {
    private static final Logger LOGGER = LogManager.getLogger(BikeController.class);

    private BikeTypeService bikeTypeService;
    private RentalPointService rentalPointService;
    private BikeService bikeService;

    @Autowired
    public BikeController(BikeTypeService bikeTypeService, RentalPointService rentalPointService, BikeService bikeService) {
        this.bikeTypeService = bikeTypeService;
        this.rentalPointService = rentalPointService;
        this.bikeService = bikeService;
    }

    @PostMapping("/addBike")
    public String addBike(@Valid Bike bike, HttpServletRequest request, Model modelUi){
        Map<String, String> requestParameters = RequestParameterHandler.requestParamToMap(request);

        long bikeTypeId = Long.parseLong(requestParameters.get(RequestParameter.BIKE_TYPE_ID.parameter()));
        long rentalPointId = Long.parseLong(requestParameters.get(RequestParameter.RENTAL_POINT_ID.parameter()));
        String brand = requestParameters.get(RequestParameter.BRAND.parameter());
        String model = requestParameters.get(RequestParameter.MODEL.parameter());
        int countBikes = Integer.parseInt(requestParameters.get(RequestParameter.BIKE_COUNT.parameter()));

        BikeType bikeType = null;
        RentalPoint rentalPoint = null;

        if (bikeTypeId == 0 || rentalPointId == 0) {
            request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.NULL_RENTAL_POINT_ID_OR_BIKE_TYPE_ID.message());
            modelUi.addAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.NULL_RENTAL_POINT_ID_OR_BIKE_TYPE_ID.message());
              return "user/user";
        }
        try {
            bikeType = bikeTypeService.getById(bikeTypeId);
            rentalPoint = rentalPointService.getById(rentalPointId);
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while getting bikeType and rentalPoint for bike : ", e);
        }
        bike.setBrand(brand);
        bike.setModel(model);
        bike.setBikeType(bikeType);
        bike.setRentalPoint(rentalPoint);
        bike.setBikeStatus(BikeStatusEnum.FREE);

        try {
            bikeService.addSomeBikes(bike, countBikes);
            request.setAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.BIKE_ADDED.message());
            modelUi.addAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.BIKE_ADDED.message());
            return "user/admin";
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while adding some bikes : ", e);
            return "redirect:/error";
        }
    }
    }

