package com.example.bikerental.util;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class RequestTimeParameter {

	public static void addParam(HttpServletRequest request, LocalDateTime startDateTime) {

		LocalDateTime dateTo = LocalDateTime.now();

		long difference = ChronoUnit.SECONDS.between(startDateTime, dateTo);

		long diffSeconds = difference % 60;
		long diffMinutes = difference / 60 % 60;
		long diffHours = difference / (60 * 60) % 24;
		long diffDays = difference / (24 * 60 * 60);

		request.getSession().setAttribute(RequestParameter.SECONDS.parameter(), diffSeconds);
		request.getSession().setAttribute(RequestParameter.MINUTES.parameter(), diffMinutes);
		request.getSession().setAttribute(RequestParameter.HOURS.parameter(), diffHours);
		request.getSession().setAttribute(RequestParameter.DAYS.parameter(), diffDays);

	}

	public static double getParam(LocalDateTime orderStartDateTime){

		LocalDateTime dateTo = LocalDateTime.now();
		long difference = ChronoUnit.SECONDS.between(orderStartDateTime, dateTo);
		return Math.ceil(difference / (60 * 60));
	}

}
