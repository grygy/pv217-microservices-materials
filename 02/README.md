# 02 - Creating a REST API with Quarkus

## What is REST?

REST stands for Representational State Transfer. It is an architectural style for designing distributed systems.

### Endpoint

An endpoint is a URL that maps to a resource. The term endpoint is often used interchangeably with route, path or URL
pattern.

Examples:

- https://api.example.com/v1/employees - Employees resource
- https://api.example.com/v1/employees/1 - Employee resource with id 1

#### URL

- **Path** - The path is the part of the URL after the domain name. In the example above, the path is `/v1/employees`
  and `/v1/employees/1`. Usually used to identify a resource.
- **Query string** - The query string is the part of the URL after the path. It starts with a question mark `?` and
  followed by key-value pairs. The key-value pairs are separated by an ampersand `&`. In the example above, there is no
  query string. Usually used for filtering, sorting, pagination, etc.

### Methods

REST API is an API that uses HTTP requests to `GET`, `PUT`, `POST` and `DELETE`. There are other HTTP methods, but these
are the most common ones.

- GET - used to retrieve data from a specified resource
- POST - used to send data to a server to create/update a resource
- PUT - used to send data to a server to create/update a resource
- DELETE - used to delete a specified resource

#### Request body

The request body is the data sent by the client to the server. It is used for `POST` and `PUT` methods.

Body is usually a JSON object (and we will use JSON). Example:

```json
{
  "name": "John",
  "surname": "Doe",
  "age": 30
}
```

#### Status codes

REST APIs use HTTP status codes to indicate the status of the request. Examples:

- 200 - OK
- 201 - Created
- 400 - Bad Request
- 404 - Not Found
- 500 - Internal Server Error

### Headers

Headers are used to provide additional information about the request or the response. Examples:

- **Content-Type** - The type of the body of the request or response. Example value: `application/json`
- **Authorization** - Contains the credentials to authenticate a user-agent with a server.

And many more. See https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers for more detail information.

### Mapping Java objects to JSON

In this quarkus project we have RESTEasy Reactive with Jackson. Jackson is a Java library for serializing and
deserializing JSON objects. So we can use Java objects and Jackson will convert them to JSON and vice versa.

## Tasks

This week's lecture will focus on creating a REST API with Quarkus. The focus is on the REST API, not on the business
logic. Therefore, the business logic is simplified to a minimum.

Goal of today's lecture is to create CRUD endpoints for Flight object.

### 0. Recommended tools

#### 0.1. Install Postman

Postman is a tool for testing REST APIs. It is available for Windows, Mac and Linux. You can download it
from https://www.postman.com/downloads/.

You can also use other tools, such as curl or httpie for API testing.

Swagger UI is also a good tool for testing REST APIs. When you run the application in dev mode, you can access Swagger
on http://localhost:8079/q/swagger-ui/.

### 1. Implement GET endpoint

In `FlightResource.class` implement `GET` endpoint for retrieving all flights. The endpoint should return all flights.

#### 1.1. Test it

Then test the endpoint with Swagger/Postman. Right now it should return an empty list.

### 2. Implement POST endpoint

In `FlightResource.class` implement `POST` endpoint for creating a new flight. For now, just assume that we will receive
the id of the flight in the request body. The endpoint should return the created flight.

Check if the flight with the given id already exists. If it does, return `409 Conflict` status code (see 2.2 how to do
it).

#### 2.1. Mapping request body JSON to Java object

Thanks to Jackson, you can use Java objects directly from request body. Jackson will convert them from JSON for us.
Thus, the parameter of the method should be `Flight` object.

#### 2.2. Return `RestResponse` object

In the previous task, we returned `Flight` object. However, we want to return a `RestResponse` object. `RestResponse` is
a generic class that contains the response body and status code. We will use it to return the response body and status
code.

You can use `RestResponse` like this:

```java
// if you want to return a status error code
return RestResponse.status(Response.Status.CONFLICT);
// Or if you want to return a body
return RestResponse.ResponseBuilder.ok(flight).build();
```

#### 2.3. Test it

After you implement the endpoint, test it with Swagger/Postman. After creating a new flight, you should be able to
retrieve it with the `GET` endpoint.

Don't forget to set the `Content-Type` header to `application/json`.

Example request body:

```json
{
  "id": 1,
  "name": "Flight 456",
  "airportFrom": "JFK",
  "airportTo": "LAX",
  "departureTime": "2024-01-15T08:00:00",
  "arrivalTime": "2024-01-15T11:00:00",
  "capacity": 180,
  "status": "ACTIVE"
}
```

### 3. Implement GET endpoint with path parameter

In `FlightResource.class` implement `GET` endpoint for retrieving a single flight. The endpoint should return a flight
with the given id.

You will need to use `PathParam` annotation. Example:

```java
@GET
@Path("/{id}")
public Flight get(@PathParam("id") int id){
        // ...
        }
```

After you implement the endpoint, test it with Swagger/Postman. After creating a new flight, you should be able to
retrieve it with the `GET /flight/{id}` endpoint.

### X. Submit the solution

[//]: # (TODO after setting up github classroom)

## Further reading

- https://quarkus.io
- https://www.smashingmagazine.com/2018/01/understanding-using-rest-api/
- https://en.wikipedia.org/wiki/JSON
- https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers
- https://quarkus.io/guides/resteasy-reactive
