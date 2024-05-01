# 02 - Creating a REST API with Quarkus

## What is REST?

REST stands for Representational State Transfer on top of the HTTP protocol. It is an architectural style for designing distributed systems.

### Endpoint

An endpoint is a URL that maps to a resource. The term endpoint is often used interchangeably with route, path, or URL pattern.

Examples:

- https://api.example.com/v1/employees - Employees resource
- https://api.example.com/v1/employees/1 - Employee resource with id 1

#### URL

- **Path** - The path is the part of the URL after the domain name. In the example above, the path is `/v1/employees`  and `/v1/employees/1`. Usually used to identify a resource.
- **Query string** - The query string is the part of the URL after the path. It starts with a question mark `?` and followed by key-value pairs. The key-value pairs are separated by an ampersand `&`. In the example above, there is no query string. Usually used for filtering, sorting, pagination, etc. Example: `/v1/employees?page=2&size=10`

### Methods

REST API is an API that uses HTTP requests to `GET`, `PUT`, `POST` and `DELETE`. There are other HTTP methods, but these are the most common ones.

- GET - used to retrieve data from a specified resource
- POST - used to send data to a server to create a resource
- PUT - used to send data to a server to update a resource
- DELETE - used to delete a specified resource

#### Request body

The request body is the data sent by the client to the server. It is used for `POST` and `PUT` methods.

The body is usually a JSON object (and we will use JSON). Example:

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

And many more. See [Mozzila docs](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status) for more status codes.

### Headers

Headers are used to provide additional information about the request or the response. Examples:

- **Content-Type** - The type of the body of the request or response. Example value: `application/json`
- **Authorization** - Contains the credentials to authenticate a user-agent with a server.

And many more. See https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers for more detailed information.

### Mapping Java objects to JSON

In this quarkus project, we have a RESTEasy Reactive with Jackson extension. Jackson is a Java library for serializing and deserializing JSON objects. So we can use Java objects, and Jackson will convert them to JSON and vice versa.

### Quarkus beans and dependency injection

#### Bean

A bean is an object that is managed by the Quarkus container. Beans are created and destroyed by the container.


The main lifecycle methods of beans are:
- `@ApplicationScoped` - Created when the application starts and destroyed when the application stops.
- `@Singleton` - Similar to `@ApplicationScoped` but lacks a client proxy that allows for lazy initialization.
- `@RequestScoped` - Created when the request starts and destroyed when the request ends.
- `@Dependent` - Created when the bean is injected and destroyed when the bean is destroyed. It follows the lifecycle of the bean into which it is injected.


In our case `FlightService` is a bean that is `@ApplicationScoped`. This means that there is only one instance of the bean that will exist for the lifetime of the application.

Imagine what would happen with `Map<Integer, Flight> flights` in `FlightService` if it was `@RequestScoped`. It would be created for every request and destroyed after the request ends. So, we would lose all the data after every request.

#### Dependency injection

We can inject beans into other beans. We can do it by using `@Inject` annotation. You can see the example in `FlightResource.java`. Quarkus will handle the rest for us.

## Tasks

This exercise will focus on creating a REST API with Quarkus. The focus is on the REST API, not on the business logic. Therefore, the business logic is simplified to a minimum.

Today's exercise aims to create CRUD endpoints for the Flight object.

### 0. Recommended tools

#### 0.1. Install Postman

Postman is a tool for testing REST APIs. It is available for Windows, Mac, and Linux. You can download it from https://www.postman.com/downloads/.

You can also use other tools, such as curl or httpie for API testing.

Swagger UI is also a good tool for testing REST APIs. When you run the application in dev mode, you can access Swagger on http://localhost:8079/q/swagger-ui/.

### 1. Implement FlightService

In `FlightService.java` implement all the methods based on provided javadoc. You can also use included tests to help you what is expected.

### 2. Implement GET endpoint

In `FlightResource.java` implement `GET` endpoint for retrieving all flights. The endpoint should return all flights.

#### 2.1. Try it

Then, test the endpoint with Swagger/Postman. Right now, it should return an empty list.

### 3. Implement POST endpoint

In `FlightResource.java` implement `POST` endpoint for creating a new flight. For now, just assume that we will receive the flight id in the request body. The endpoint should return the created flight.

Check if the flight with the given id already exists. If it does, return `409 Conflict` status code (see 3.2 how to do it).

#### 3.1. Mapping request body JSON to Java object

Thanks to Jackson, you can use Java objects directly from the request body. Jackson will convert them from JSON for us. Thus, the parameter of the method should be `Flight` object.

#### 3.2. Return `RestResponse` object

In the previous task, we returned `Flight` object. However, we want to return a `RestResponse` object. `RestResponse` is a generic class that contains the response body and status code. We will use it to return the response body and status code.

You can use `RestResponse` like this:

```java
// if you want to return a status error code
return RestResponse.status(Response.Status.CONFLICT);
// Or if you want to return a body
return RestResponse.ResponseBuilder.ok(flight).build();
```

#### 3.3. Try it

After you implement the endpoint, try it with Swagger/Postman. After creating a new flight, you should be able to retrieve it with the `GET` endpoint.

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

### 4. Implement GET endpoint with a path parameter

In `FlightResource.java` implement `GET` endpoint for retrieving a single flight. The endpoint should return a flight with the given id.

You will need to use `PathParam` annotation. Example:

```java
@GET
@Path("/{id}")
public Flight get(@PathParam("id") int id){
        // ...
        }
```

After you implement the endpoint, try it with Swagger/Postman. After creating a new flight, you should be able to retrieve it with the `GET /flight/{id}` endpoint.

### 5. Implement PUT endpoint

In `FlightResource.java` implement `PUT` endpoint for updating a flight. Don't forget to check if the flight exists.

### 6. Submit the solution

1. Finish the tasks
2. Push the changes to the main branch
3. GitHub Classroom automatically prepared a feedback pull request for you
4. GitHub Actions will run basic checks for your submission on push
5. Teacher will evaluate the submission as well and give you feedback

Resubmit the solution if the checks fail:
1. Make changes
2. Push again

## Hints

- See other methods in `FlightResource.java` for examples.

## Further reading

- https://quarkus.io
- https://www.smashingmagazine.com/2018/01/understanding-using-rest-api/
- https://en.wikipedia.org/wiki/JSON
- https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers
- https://quarkus.io/guides/resteasy-reactive
- https://quarkus.io/guides/cdi