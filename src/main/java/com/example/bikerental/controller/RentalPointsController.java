package com.example.bikerental.controller;

import com.example.bikerental.entity.RentalPoint;
import com.example.bikerental.exception.ServiceException;
import com.example.bikerental.service.BikeService;
import com.example.bikerental.service.RentalPointService;
import com.example.bikerental.util.PageConstant;
import com.example.bikerental.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rentalPoint")
public class RentalPointsController {

    private static Logger LOGGER = LogManager.getLogger(RentalPoint.class);

    @Autowired
    private RentalPointService rentalPointService;
    @Autowired
    private BikeService bikeService;

    @GetMapping("/contacts")
    public String contacts(Model model) {
        List<RentalPoint> rentalPoints;
        Map<String, String> bikesByType;
        Map<Long, Map<String, String>> freeBikesByRentalPoint = new HashMap<>();
        try {
            rentalPoints = rentalPointService.getRentalPoints();
            model.addAttribute(RequestParameter.RENTAL_POINT_LIST.parameter(), rentalPoints);
            for (RentalPoint rentalPoint : rentalPoints) {
                bikesByType = bikeService.getAvailableBikesByRentalPointId(rentalPoint.getId());
                freeBikesByRentalPoint.put(rentalPoint.getId(), bikesByType);
            }

            model.addAttribute("freeBikesByRentalPoint", freeBikesByRentalPoint);
        } catch (ServiceException e) {
            LOGGER.error("Get all rental points exception " + e.getMessage());
        }
        return PageConstant.CONTACTS_PAGE;
    }


    @GetMapping("/allRentalPoints")
    public String allRentalPoints(Model model) {
        List<RentalPoint> rentalPoints;
        try {
            rentalPoints = rentalPointService.getRentalPoints();
            model.addAttribute(RequestParameter.RENTAL_POINT_LIST.parameter(), rentalPoints);
        } catch (ServiceException e) {
            LOGGER.error("Get all rental points exception " + e.getMessage());
        }
        return PageConstant.RENTAL_POINTS_PAGE;
    }
}
