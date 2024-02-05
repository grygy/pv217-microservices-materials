# 04 - Testing

## Why are tests important?

Test are a crucial part of software development. Test ensure that the code you write is working as expected. They also help you to understand the code and how to compose it. Easily testable code is also easier to maintain and refactor.

Key benefits of testing:
- Detecting bugs early
- Ensuring code quality
- Facilitating refactoring
- Tests are a form of documentation
- Simplifying collaboration

### Best friends

AI tools are great for generating basic test and data. BE AWARE that they are not perfect, and you should always review the generated code and change it to actually fit your needs.

- ChatGPT
- Copilot 

## Unit tests

The most common type of tests are unit tests. The key idea of unit tests is to test the smallest piece of code possible in isolation. Usually, this means testing a single method or function and checking if for given input it returns the expected output.

Key attributes of unit tests:
- Fast
- Isolated
- Simple

### `QuarkusTest` annotation

Quarkus provides a `QuarkusTest` annotation that allows you to write tests for your Quarkus application. It starts the application in a test mode and provides you with a test client to interact with the application. It also allows you to inject beans and other resources into your tests.

### Mocking

But how to isolate the code from the rest of the system? The answer is mocking. Mocking is a technique used to isolate the code under test from the rest of the system. It is used to replace the real dependencies of the code with fake ones. This allows you to test the code in isolation.

## Integration tests

Integration tests are used to test the interaction between different parts of the system. Usually it means testing flows in the system and checking if the system behaves as expected. In our case of `passenger-service` it could mean testing if the passenger can be created and then retrieved from the database using the REST API. This tests every layer of the system. 

They ensure that the system is set up correctly also in production environment.

### `@QuarkusIntegrationTest` annotation

Quarkus provides a `@QuarkusIntegrationTest` annotation that allows you to write integration tests for your Quarkus application. The tests are run after the build using the prod configuration profile. 

It's a black box in terms of the actual execution but thanks to the dev services and the test containers it's possible to test the whole system in production-like environment.

## Technologies

We will depend on the following technologies to write tests:
- JUnit 5 -- Unit tests
- RestAssured -- REST API testing
- Mockito -- Mocking
- Test vertx -- Testing asynchronous code

### JUnit

JUnit is a simple framework to write repeatable tests. 

#### Examples

Basic

```java
@Test // Basic synchronous test
public void testBasicFunctionality() {
    SimpleService service = new SimpleService();
    assertEquals("Expected output", service.doSomething()); // <expected>, <actual>
}

@Test
@RunOnVertxContext // Test running on vertx context (asynchronous)
public void testSimpleUni(UniAsserter asserter) { // Gives us UniAsserter to assert the result from Uni
    asserter.assertThat(
        () -> Uni.createFrom().item("Hello"), // Asynchronous code or function
        result -> assertEquals("Hello", result) // When the result is available, assert it
    );
}

@Test
@TestReactiveTransaction // Similar to RunOnVertxContext, but for reactive transactions when we need to manipulate with the database
public void testReactiveTransaction(UniAsserter asserter) {
    asserter.assertThat(
        () -> Uni.createFrom().item("Expected Result"),
        result -> assertEquals("Expected Result", result)
    );
}
```

Mocked

```java
@QuarkusTest
public class MockedServiceUniTest {

    @InjectMock // Similar to Inject, but injects a mock instead of a real instance
    DataService dataService;
    
    @Inject
    MyService myService; // My service is using DataService

    @Test
    public void testServiceWithMock() {
        Mockito.when(dataService.getData()).thenReturn("Mocked Data");
        assertEquals("Mocked Data", myService.retrieveData());
    }

    @Test
    @RunOnVertxContext
    public void testUniWithMockService(UniAsserter asserter) {
        // Now mock the data service (asynchronous) to return a specific value
        asserter.execute(() ->  Mockito.when(dataService.getDataAsUni()).thenReturn(Uni.createFrom().item("Mocked Data")));

        asserter.assertThat(
            () -> dataService.getDataAsUni(),
            result -> assertEquals("Mocked Data", result)
        );
    }
}
```

### RestAssured

RestAssured is a Java library that provides a testing REST APIs. It's a great tool to implement integration tests but also unit tests for REST endpoints.

#### Examples

```java
@QuarkusTest
@TestHTTPEndpoint(GreetingResource.class) // Which endpoint to test
public class GreetingResourceTest {
    
    // We can also mock underlying services if needed
    
    @Test
    public void testHelloEndpoint() {
        when() // When we call the endpoint
            .get() // With GET method 
            .then() // Then
            .statusCode(200) // Expecting status code 200
            .body(is("hello")); // And the body to be "hello"
    }

    @Test
    public void testCreateEntityEndpoint() {
        // Example entity
        MyEntity entity = new MyEntity();
        entity.name = "Sample Name";
        entity.description = "Sample Description";

        given()
                .contentType("application/json") // Set content type
                .body(entity) // Set the body
                .when() // When we call the endpoint
                .post("/entities") // Call the POST endpoint
                .then()
                .statusCode(201)
                .body("id", notNullValue()) // Expecting the id to be not null
                .body("name", equalTo("Sample Name")) // Further body checks
                .body("description", equalTo("Sample Description")); // Further body checks
    }
}
```

## Tasks

### 0. Running docker

Install [Docker desktop](https://docs.docker.com/desktop/) or other docker client. Our test database will run in docker container.

### 1. Implement unit tests for `PassengerRepository`

Go to `PassengerRepositoryTest` and implement the todo's. You don't need to mock anything because Quarkus provides a test database for you.

Don't forget to add at least two of your own tests.

#### X.y. How to test if everything is working?

- Tests are passing

Test scenario
- Create a flight using swagger ui
- Create a passenger using swagger ui with appropriate flight id
- Call cancel flight endpoint
- Check if the GET notification endpoint returns the notification for the passenger with his email.

### X. Submit the solution

[//]: # (TODO after setting up github classroom)

## Hints

- You can inspire yourself from tests in `flilght-service`.

## Troubleshooting

- Check if your docker engine is running.

## Further reading

- https://quarkus.io/guides/getting-started-testing