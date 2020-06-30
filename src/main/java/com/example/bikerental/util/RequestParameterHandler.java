package com.example.bikerental.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestParameterHandler {
    public static Map<String, String> requestParamToMap(HttpServletRequest request) {

        Map<String, String[]> parameters = request.getParameterMap();

        Map<String, String> requestParameters = new HashMap<>();

        Set<Map.Entry<String, String[]>> set = parameters.entrySet();

        for (Map.Entry<String, String[]> mapEntry : set) {

            if (!RequestParameter.PASSWORD.parameter().equals(mapEntry.getKey())) {
                for (String value : mapEntry.getValue()) {
                    requestParameters.put(mapEntry.getKey(), value);
                }
            }
        }
        return requestParameters;
    }

    public static String paramToString(HttpServletRequest request) {

        String urlWithParam = request.getServletPath();

        Map<String, String[]> parametrs = request.getParameterMap();
        Set<Map.Entry<String, String[]>> set = parametrs.entrySet();

        int countParams = 0;
        for (Map.Entry<String, String[]> i : set) {

            for (String value : i.getValue()) {
                if (countParams == 0) {
                    urlWithParam = urlWithParam + "?" + i.getKey() + "=" + value;
                    countParams++;
                } else {
                    urlWithParam = urlWithParam + "&" + i.getKey() + "=" + value;
                }
            }
        }
        return urlWithParam;
    }

    public static void addParamToRequest(HttpServletRequest request) {

        Map<String, String[]> parametrs = request.getParameterMap();
        Set<Map.Entry<String, String[]>> set = parametrs.entrySet();

        for (Map.Entry<String, String[]> mapEntry : set) {

            if (!RequestParameter.PASSWORD.parameter().equals(mapEntry.getKey())) {
                for (String value : mapEntry.getValue()) {
                    request.setAttribute(mapEntry.getKey(), value);
                }
            }

        }

    }
}
