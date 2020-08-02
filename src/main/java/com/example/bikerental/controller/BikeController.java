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
import com.example.bikerental.util.PageConstant;
import com.example.bikerental.util.PageMessage;
import com.example.bikerental.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    public String addBike(@Valid Bike bike, Errors errors, HttpServletRequest request, Model modelUi) {

        long bikeTypeId = bike.getBikeType().getId();
        long rentalPointId = bike.getRentalPoint().getId();
        String brand = bike.getBrand();
        String model = bike.getModel();
        int countBikes = Integer.parseInt(request.getParameter("bikeCount"));

        BikeType bikeType = null;
        RentalPoint rentalPoint = null;

        if (bikeTypeId == 0 || rentalPointId == 0) {
            request.setAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.NULL_RENTAL_POINT_ID_OR_BIKE_TYPE_ID.message());
            modelUi.addAttribute(RequestParameter.ERROR.parameter(), ExceptionMessage.NULL_RENTAL_POINT_ID_OR_BIKE_TYPE_ID.message());
            return "user/admin";
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
            return "redirect:/user";
        } catch (ServiceException e) {
            LOGGER.error("Exception occurred while adding some bikes : ", e);
            return "redirect:/error";
        }
    }

    @GetMapping("/bikes")
    public String bikes(Model model) {
        try {
            List<Bike> bikes = bikeService.getAll();
            model.addAttribute(RequestParameter.BIKE_LIST.parameter(), bikes);
        } catch (ServiceException e) {
            LOGGER.error("Get all bike exception " + e.getMessage());
            return "redirect:/error";
        }
        return PageConstant.BIKES_PAGE;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bikeId") String bikeId, HttpServletRequest request) {

        try {
            bikeService.deleteBikeById(bikeId);
            request.setAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.BIKE_DELETED.message());
        } catch (ServiceException e) {
            LOGGER.error("An error occurred while the bike was deleting, " + e.getMessage());
            return "redirect:/error";
        }
        return "redirect:/bike/bikes";
    }

    @GetMapping("/changeStatus")
    public String changeStatus(@RequestParam("bikeId") String bikeId,
                               @RequestParam("status") String status,
                               HttpServletRequest request) {
        try {
            bikeService.changeStatusById(bikeId, status);
            request.setAttribute(RequestParameter.MESSAGE.parameter(), PageMessage.BIKE_STATUS_CHANGED.message());
        } catch (ServiceException e) {
            LOGGER.error("An error occurred while the bike was changing state , " + e.getMessage());
            request.setAttribute(RequestParameter.ERROR.parameter(), e.toString());
        }
        return "redirect:/bike/bikes";
    }
}

