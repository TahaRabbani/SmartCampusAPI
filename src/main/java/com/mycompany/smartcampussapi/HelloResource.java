package com.mycompany.smartcampussapi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

    @GET
    public Map<String, Object> getApiInfo() {

        Map<String, Object> response = new HashMap<>();

        response.put("name", "Smart Campus API");
        response.put("version", "1.0");
        response.put("admin", "admin@university.com");

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("rooms", "/api/v1/rooms");
        endpoints.put("sensors", "/api/v1/sensors");

        response.put("endpoints", endpoints);

        return response;
    }
}
