# 03 - OpenAPI specification and gRPC

## What is the role of API?

The main role of Application programming interface (API) is to allow easy communication between different software
components. It should describe the functionality of the component and the way it can be used. The API should be easy to
use and understand.

### Contract first approach

API is a first-class citizen meaning that it should be designed before the implementation. This approach is called
contract first. The contract is a document that describes the API. The most popular format to define API documentation
is OpenAPI.

It's a good approach to spend more time on designing the API so other services (and teams) could rely on it when
implementing their own services. It shouldn't be changed. If it's necessary to change the API, it should be done in a
backward compatible way. Eg. versioning the api like `/api/v1/...` and `/api/v2/...`.

#### Benefits of contract first approach

- Teams can work in parallel on business logic since the API is already defined
- The API is designed with the client in mind
- No unused endpoints

#### Drawbacks of contract first approach

- Limited flexibility for changes in the API
- Upfront time and effort to design the API

### Code first approach

The code first approach is the opposite of the contract first approach. First the code is written and then the API is
generated from the code.

#### Benefits of code first approach

- Easy to start
- Flexible with change the API
- The API is always up to date

#### Drawbacks of code first approach

- The API is not designed with the client in mind
- May result in unused endpoints that are just "hanging around"

## OpenAPI specification

OpenAPI is a specification standard for describing REST APIs. It's a language-agnostic format that can be used to
describe APIs for different programming languages.

It is an open standard for describing your APIs, allowing you to provide an API specification encoded in a JSON or YAML
document embedding the fundamentals of HTTP and JSON.

### OpenAPI in Quarkus

Quarkus supports OpenAPI specification generation from the code via `quarkus-smallrye-openapi` extension that we
installed in the first lecture.

We can extend OpenAPI specification with additional information using `@OpenAPIDefinition` annotation for the whole
application.

```java

@OpenAPIDefinition(
        tags = {
                @Tag(name = "widget", description = "Widget operations."),
                @Tag(name = "gasket", description = "Operations related to gaskets")
        },
        info = @Info(
                title = "Example API",
                version = "1.0.1",
                contact = @Contact(
                        name = "Example API Support",
                        url = "http://exampleurl.com/contact",
                        email = "techsupport@example.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class FlightServiceApplication extends Application {
    //...
}
```

Note that `FlightServiceApplication` extends `Application` class from `jakarta.ws.rs.core.Application`. It's kinda "
entry point" for application. It's not necessary to extend this class, but it's required for the use
of `@OpenAPIDefinition`.

We can also provide additional information for resources using `@Tag` annotation.

```java

@Tag(name = "Flight resource", description = "Provides Flight CRUD operations")
public class FlightResource {
    //...
}
```

When you will run `quarkus dev` in flight-service, you can see the OpenAPI specification in the browser
at http://localhost:8079/q/openapi.

## What is gRPC?

gRPC is a high performance, open-source universal Remote Procedure Call (RPC) framework. It's based on HTTP/2 and
Protocol Buffers.

### Main features:

- HTTP/2 based - low latency, multiplexing, header compression, stream prioritization
- High performance - binary protocol, efficient serialization, asynchronous by default

### Differences between REST and gRPC

| REST          | gRPC                         |
|---------------|------------------------------|
| HTTP/1.1      | HTTP/2                       |
| Text based    | Binary                       |
| JSON, XML     | Protocol Buffers             |
| Stateless     | Bidirectional streaming      |
| Client-server | Client-server, server-server |
| HTTP verbs    | RPC methods                  |

## Tasks

### 1.

### X. Submit the solution

[//]: # (TODO after setting up github classroom)

## Hints

-

## Further reading

- https://quarkus.io
- https://www.openapis.org/
- https://swagger.io/resources/articles/adopting-an-api-first-approach/
- https://www.visual-paradigm.com/guide/development/code-first-vs-design-first/ 