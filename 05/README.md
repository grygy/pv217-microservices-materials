# 05 - Testing

## Why are tests necessary?

Tests are a crucial part of software development. Tests ensure that the code you write is working as expected. They also help you to understand the code and how to compose it. Easily testable code is also easier to maintain and refactor.

Key benefits of testing:
- Detecting bugs early
- Ensuring code quality
- Facilitating refactoring
- Tests are a form of documentation
- Simplifying collaboration

### Best friends

AI tools are great for generating basic tests and data. BE AWARE that they are not perfect, and you should always review the generated code and change it to actually fit your needs.

- ChatGPT
- Copilot
- Intellij IDEA assistant

## Unit tests

The most common type of tests are unit tests. The key idea of unit tests is to test the smallest piece of code possible in isolation. Usually, this means testing a single method or function and checking if, for a given input, it returns the expected output.

Key attributes of unit tests:
- Fast
- Isolated
- Simple

### `QuarkusTest` annotation

Quarkus provides a `QuarkusTest` annotation that allows you to write tests for your Quarkus application. It starts a new instance of the application in a test mode and provides you with a test client to interact with the application. It also allows you to inject beans and other resources into your tests.


### Mocking

But how do we isolate the code from the rest of the system? The answer is mocking. Mocking is a technique used to isolate the code under test from the rest of the system. It is used to replace the real dependencies of the code with fake ones. This allows you to test the code in isolation.

## Integration tests

Integration tests are used to test the interaction between different parts of the system. Usually, this means testing the system's flow and checking if the system behaves as expected. In our case of `passenger-service` it could mean testing if the passenger can be created and then retrieved from the database using the REST API. This tests every layer of the system.

They ensure that the system is set up correctly and also in a production environment.

### `@QuarkusIntegrationTest` annotation

Quarkus provides a `@QuarkusIntegrationTest` annotation that allows you to write integration tests for your Quarkus application. These tests are executed against the build artifact -- whether it's a JAR file, a native executable, or a container image -- using the production configuration profile.

It's a black box in terms of the actual execution, but thanks to the dev services and the test containers, it's possible to test the whole system in a production-like environment.

## Technologies

We will depend on the following technologies to write tests:
- JUnit 5 -- Unit tests
- RestAssured -- REST API testing
- Mockito -- Mocking
- Test Vert.x -- Testing asynchronous code

#### Examples

Basic

```java
@Test // Basic synchronous test
public void testBasicFunctionality() {
        SimpleService service = new SimpleService();
        // <expected>, <actual>, <message>
        assertEquals("Expected output", service.doSomething(), "Service did not return the expected output.");
        }

@Test
@RunOnVertxContext // Test running on vertx context (asynchronous)
public void testSimpleUni(UniAsserter asserter) { // Gives us UniAsserter to assert the result from Uni
        asserter.assertThat(
        () -> Uni.createFrom().item("Hello"), // Asynchronous code or function
        result -> assertEquals("Hello", result, "Did not return hello") // When the result is available, assert it
        );
        }

@Test
@TestReactiveTransaction // Similar to RunOnVertxContext, but for reactive transactions when we need to manipulate with the database
public void testReactiveTransaction(UniAsserter asserter) {
        asserter.assertThat(
        () -> Uni.createFrom().item("Expected Result"),
        result -> assertEquals("Expected Result", result, "Did not return expected result.")
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
        assertEquals("Mocked Data", myService.retrieveData(), "Did not return mocked data");
    }

    @Test
    @RunOnVertxContext
    public void testUniWithMockService(UniAsserter asserter) {
        // Now mock the data service (asynchronous) to return a specific value
        asserter.execute(() ->  Mockito.when(dataService.getDataAsUni()).thenReturn(Uni.createFrom().item("Mocked Data")));

        asserter.assertThat(
                () -> myService.getDataAsUni(),
                result -> assertEquals("Mocked Data", result, "Did not return mocked data")
        );
    }
}
```

### RestAssured

RestAssured is a Java library for testing REST APIs. It's a great tool to implement integration tests and unit tests for REST endpoints.

#### Examples

```java
@QuarkusTest
@TestHTTPEndpoint(GreetingResource.class) // (Optional) Which endpoint to test
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

Install [Docker desktop](https://docs.docker.com/engine/install/) or another docker client. Our test database will run in a docker container.

### 1. Implement unit tests for `PassengerRepository`

Go to `PassengerRepositoryTest` and implement the todos. You don't need to mock anything because Quarkus provides a test database for you.

Don't forget to add at least two of your own tests.

### 2. Implement unit tests for `PassengerService`

Go to `PassengerServiceTest` and implement the todo's. You will need to mock the `PassengerRepository` to isolate the service from the database.

Don't forget to add at least two of your own tests.

### 3. Implement unit tests for `PassengerResource`

Go to `PassengerResourceTest` and implement the todo's. You will need to mock the `PassengerService` to isolate the resource from the service.

### 4. Implement integration tests for `PassengerResource`

Go to `PassengerResourceTestIT` and implement the todo's. One test has already been implemented; use it as an example. In this test, you don't need to mock anything; you will test the whole system.

### 5. Verify if everything is working

Run the following command at the root of the project:

```bash
mvn verify -DskipITs=false
```

If everything is working, all tests should pass, and you are ready to submit the solution.

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

- You can get some inspiration from tests in `flight-service`.
- Create helper methods in tests to create example objects.

## Troubleshooting

- Check if your docker engine is running.

## Further reading

- https://quarkus.io/guides/getting-started-testing