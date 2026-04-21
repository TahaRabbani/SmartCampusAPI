/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampussapi;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://localhost:8081/";

    public static HttpServer startServer() {

        final ResourceConfig rc = new ResourceConfig()
                .register(HelloResource.class)
                .register(RoomResource.class)
                .register(SensorResource.class)
                .register(GlobalExceptionMapper.class)
                .register(LoggingFilter.class)
                .register(RoomNotEmptyMapper.class)
                .register(LinkedResourceMapper.class)
                .register(SensorUnavailableMapper.class);   // 🔥 FORCE register

        return GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI + "api/v1"), rc
        );
    }

    public static void main(String[] args) {
        final HttpServer server = startServer();
        System.out.println("Server started at " + BASE_URI + "api/v1");
    }
}
