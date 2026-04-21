package com.mycompany.smartcampussapi;

import com.mycompany.smartcampussapi.model.Sensor;
import com.mycompany.smartcampussapi.model.SensorReading;
import com.mycompany.smartcampussapi.storage.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    // 🔥 GET all readings
    @GET
    public Response getReadings() {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        return Response.ok(sensor.getReadings()).build();
    }

    // 🔥 ADD reading
    @POST
    public Response addReading(SensorReading reading) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        if ("MAINTENANCE".equals(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor under maintenance.");
        }

        // add reading to list
        sensor.getReadings().add(reading);

        // update current value
        sensor.setCurrentValue(reading.getValue());

        return Response.ok(reading).build();
    }
}
