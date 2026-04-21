# SmartCampusAPI
Smart Campus REST API using JAX-RS


# SmartCampusAPI

## Overview

This project implements a **RESTful API for a Smart Campus system** using JAX-RS (Jersey) and an embedded Grizzly HTTP server.

The API manages three main entities:

* **Rooms** → Physical locations (lecture halls, labs)
* **Sensors** → Devices inside rooms (CO2, temperature, etc.)
* **Sensor Readings** → Historical data collected by sensors

The relationship is:

Room → contains Sensors → which store Readings

The API supports full interaction including creation, retrieval, validation, nested resources, and advanced error handling.

---

## How to Run the Project

1. Open the project in NetBeans or any Java IDE
2. Ensure JDK is installed (JDK 11+ recommended)
3. Locate the `Main.java` file
4. Run `Main.java`
5. The server will start at:

```
http://localhost:8081/api/v1
```

6. Use Postman or browser to test endpoints

---

## Sample curl Commands

### 1. Get all rooms

```bash
curl -X GET http://localhost:8081/api/v1/rooms
```

### 2. Create a room

```bash
curl -X POST http://localhost:8081/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"R1","name":"Lecture Hall","capacity":100}'
```

### 3. Create a sensor

```bash
curl -X POST http://localhost:8081/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"S1","type":"CO2","status":"ACTIVE","currentValue":50,"roomId":"R1"}'
```

### 4. Get filtered sensors

```bash
curl -X GET "http://localhost:8081/api/v1/sensors?type=CO2"
```

### 5. Add sensor reading

```bash
curl -X POST http://localhost:8081/api/v1/sensors/S1/readings \
-H "Content-Type: application/json" \
-d '{"id":"R001","timestamp":1710000000,"value":55}'
```
### 6. Delete Room
```bash
curl -X DELETE http://localhost:8081/api/v1/rooms/R1
```
### 7. INVALID sensor (422 test)
```bash
curl -X POST http://localhost:8081/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"S99","type":"CO2","status":"ACTIVE","currentValue":50,"roomId":"INVALID"}'
```
---

# Answers to Coursework Questions

---

## Part 1

### Resource Lifecycle

In JAX-RS, resource classes are **by default instantiated per request**. This means a new object is created for each incoming HTTP request.

This design ensures thread safety, as each request operates on its own instance. However, shared data structures such as HashMaps must be carefully managed because they are global. Without proper synchronization, concurrent access can cause race conditions or data inconsistency.

---

### HATEOAS (Hypermedia)

HATEOAS allows APIs to include links within responses so clients can dynamically navigate the system.

This improves flexibility because:

* Clients do not rely on hardcoded URLs
* APIs become self-descriptive
* Changes in endpoints do not break clients

Compared to static documentation, HATEOAS makes APIs more adaptive and easier to use.

---

## Part 2

### Returning IDs vs Full Objects

Returning only IDs reduces network usage but forces clients to make additional requests.

Returning full objects increases payload size but improves performance by reducing extra calls. Therefore, full objects are usually preferred for better usability.

---

### DELETE Idempotency

DELETE is idempotent because repeating the same request produces the same result.

If a room is deleted:

* First request → deletes the room
* Second request → room no longer exists

The final state remains unchanged, satisfying idempotency.

---

## Part 3

### @Consumes JSON Handling

If a client sends data in a format other than JSON (e.g., text/plain or XML), JAX-RS cannot match it with the method.

This results in an HTTP **415 Unsupported Media Type** error, ensuring only valid formats are processed.

---

### QueryParam vs Path

Using query parameters is better for filtering because:

* It allows optional filtering
* It supports multiple filters easily
* It keeps URLs clean and flexible

Using path parameters is better suited for identifying specific resources, not filtering collections.

---

## Part 4

### Sub-Resource Locator Benefits

The sub-resource locator pattern allows delegation of logic to separate classes.

Benefits:

* Cleaner code structure
* Better separation of concerns
* Easier maintenance
* Improved scalability for large APIs

Instead of one large class, responsibilities are divided logically.

---

### Historical Data Management

The API stores sensor readings and allows retrieval of historical data.

When a new reading is added:

* It is appended to the sensor’s readings list
* The sensor’s `currentValue` is updated

This ensures consistency between current and historical data.

---

## Part 5

### Why 422 instead of 404

HTTP 422 is more accurate because:

* The request format is correct
* But the referenced resource (roomId) is invalid

404 implies the endpoint is missing, whereas 422 correctly indicates invalid data.

---

### Security Risks of Stack Traces

Exposing stack traces can reveal:

* Internal class names
* File paths
* System structure

Attackers can use this information to exploit vulnerabilities. Therefore, APIs must hide internal errors and return generic responses.

---

### Logging with Filters

Using JAX-RS filters is better because:

* It centralizes logging logic
* Avoids repeating code in every method
* Ensures consistency
* Improves maintainability

Filters handle cross-cutting concerns efficiently.

---

The API also includes logging using JAX-RS filters,
which records each request method, URI, and response status
in the server console for monitoring and debugging.
