# 01 - Introduction to Quarkus

## What is Quarkus?

Quarkus is an open-source Kubernetes-native Java framework tailored for GraalVM and HotSpot, crafted from best-of-breed Java libraries and standards. The goal is to make Java the leading platform in Kubernetes and serverless environments while offering developers a framework to address a broader range of distributed application architectures.

## How is Quarkus different from Spring boot

### Spring Boot

- Mature framework with a large community. Focus on microservices and enterprise applications.
- Accelerates developer productivity with production-ready integrations.
- Reduces configuration and boilerplate through convention over configuration.
- Supports both blocking and non-blocking web capabilities but not simultaneously.

### Quarkus

- Similar to Spring Boot but emphasizes fast boot time, smaller memory footprint, and lower resource usage.
- Optimized for cloud, serverless, and containerized environments.
- Integrates well with popular Java frameworks.
- Offers simultaneous both blocking and non-blocking strategies.
- Offers Dev UI and live reload for rapid development and testing.
- Offers a unified reactive API for both imperative and reactive programming styles.
- Offers Native Images for even faster startup and lower memory footprint.
- Offers easy native compilation, which creates Native Images for even faster startup and lower memory footprint.

## Quarkus Technologies

- **Maven** or **Gradle** to create a new project, add or remove extensions, launch development mode, debug your
  application, and build your application.
- **Jakarta EE** to write your business logic and REST endpoints.
- **MicroProfile** to health check, monitor, and configure your application.

## Tasks

This exercise will be more about reading and understanding the Quarkus developer experience than about coding. We will create a simple Quarkus application and run it in development mode.

### 0. Prerequisites

Install Java 21

1. Install OpenJDK 21
   Find the latest version of OpenJDK 21 [here](https://jdk.java.net/21/). Installation instruction for different operating systems can be found [here](https://www.freecodecamp.org/news/install-openjdk-free-java-multi-os-guide/).

2. Check the Java version
    ```bash
    java --version
   # should be 21
    ```

3. Install Maven

   Find the latest version of Maven [here](https://maven.apache.org/download.cgi).

   Or
    ```bash
    # linux
    sudo apt install maven
        
    # mac
    brew install maven
        
    # windows
    scoop install maven
    ```
4. Verify Maven installation
    ```bash
    mvn --version
    ```
5. Install the latest version of IntelliJ IDEA to mitigate issues with the IDE and Quarkus.
   - Install IntelliJ IDEA from [here](https://www.jetbrains.com/idea/download/).

### 1. Install Quarkus CLI

Even though Quarkus application can be created using Maven, Quarkus CLI is a convenient tool to create and manage Quarkus applications.

1. Install Quarkus CLI
   - You can find more installation options [here](https://quarkus.io/guides/cli-tooling).
       ```bash
       # linux 
       curl -Ls https://sh.jbang.dev | bash -s - trust add https://repo1.maven.org/maven2/io/quarkus/quarkus-cli/
       curl -Ls https://sh.jbang.dev | bash -s - app install --fresh --force quarkus@quarkusio
   
       # mac Homebrew
       brew install quarkusio/tap/quarkus
   
       # windows Scoop
       scoop install quarkus-cli
       ```

2. Check Quarkus CLI version
    ```bash
    quarkus --version
    # should be >= 3.6.0
    ```

### 2. Create a Quarkus application

1. Create a Quarkus application

   Let's use `quarkus create app <groupID>:<artifactID>` to create a Quarkus application.
   - `groupID` is the name of the application package. It is usually the reverse domain name of the organization.
   - `artifactID` is the application's name (and .jar file).
    ```bash
    quarkus create app cz.muni.fi:flight-service
    ```

2. Explore the app

   Open the project in IntelliJ IDEA or your favorite IDE.

   The folder `flight-service` should be created. It contains the following files:
   - `pom.xml` - This is the Maven project file. It contains information like the project's name, version,
     dependencies, and plugins. It's essential for building and managing the project.
   - `src/main/resources/application.properties` - This file is used for application configuration. You can define
     various settings related to your application here.
   - `src/main/java/cz/muni/fi//GreetingResource.java` - This is a REST endpoint. It's a simple Java class that handles
     HTTP requests and returns responses.
   - `src/test/java/cz/muni/fi//GreetingResourceTest.java` - This is a unit test for the REST endpoint. It helps ensure
     your code works as expected.
   - `src/test/java/cz/muni/fi//GreetingResourceIT.java` - This is an integration test for the REST endpoint. It tests
     how different parts of the application work together.

### 3. Run the application

```bash
cd flight-service
quarkus dev
```

The application should download dependencies and start on port 8080. You can access the basic REST endpoint at http://localhost:8080/hello. It should return "*Hello from RESTEasy Reactive*".

### 4. What is Quarkus development mode?

Quarkus development mode is a powerful feature that makes Quarkus development fast and easy. It offers the following features:

#### 4.1. Live reload

Quarkus automatically reloads the application when you change the configuration. So, no more restarting the application after every change.

#### 4.2. Dev UI

You can find Dev UI at http://localhost:8080/q/dev-ui. It offers the following features:

- Extension visualization - Shows which extensions are used in the application.
- Extension documentation - Shows documentation for the extensions used in the application.
- Configuration visualization - Shows which configuration properties are used in the application. You can also change
  the configuration and see the changes in the application.
- Continuous testing visualization - Shows the status of the tests in graphical form.
- Dev Services - Starts and stops external services like databases, Kafka, etc. You don't need to setup a database for testing. Quarkus will do it for you.
- Build metrics - Shows how long it takes to build the application.

#### 4.3. Quarkus dev CLI

When you look at the console, you can see that Quarkus runs tests after every change. This is called continuous testing. It helps you to find bugs as soon as possible.

- You can also run tests manually by pressing `r` in the console.
- You can press `s` to force restart the application.

Press `h` to see all available commands and try some of them.

### 5. Change the code

Try to change the code, refresh the site, and see how Quarkus reacts. For example, you can change the message in the `hello()` method in `GreetingResource.java` and see how it changes in the browser. You will see live reload in action with continuous testing.

1. Go to `GreetingResource.java` and change the message in the `hello()` method.
2. Go to `GreetingResourceTest.java` and change the test to match the new message.
3. Now the test should pass again and http://localhost:8080/hello should return "*Hello*".

### 6. Configure the application

In `application.properties`, you can configure the application. For example, you can change the port on which the application runs.

Try adding the following line to `application.properties` and see how it changes in the browser.

```properties
quarkus.http.port=8079
```

Now, kill the application and run it again. You should see that the application runs on port 8079.

In `application.properties` you can also configure the configuration of the extensions or add your own configuration. We will use it much more in the following exercises.

#### 6.1. Separate configuration for development and production

For each property, you can define a separate value for development and production by prefix. For example, you can define a different port for development and production.

Prefixes:

- without prefix - default
- `%dev` - development
- `%test` - test
- `%prod` - production

Add the following lines to `application.properties` and restart the dev mode of the application. The application should still run on port 8079.

```properties
quarkus.http.port=8080
%dev.quarkus.http.port=8079
```

#### 6.2. Using configuration in the code

You can inject configuration into your code using `@ConfigProperty` annotation. For example, you can inject the port into the `GreetingResource.java` class.

Add the following property to `GreetingResource.java` class and import the `ConfigProperty`.

```java
@ConfigProperty(name = "quarkus.http.port")
int port;
```

Now, you can use the `port` variable in the `hello()` method.

```Java
@GET
@Produces(MediaType.TEXT_PLAIN)
public String hello(){
        return"Hello on "+port;
        }
```

Repair the unit test in `GreetingResourceTest.java` by adding the port property to `GreetingResourceTest` class in the same way as in the `GreetingResource` class.

### 7. Add extension

Quarkus offers many extensions that you can add to your application. You can find the list of extensions [here](https://quarkus.io/extensions/).

Currently, we already have some extensions present in the application. You can find them in the `pom.xml` file. For example, the `REST` extension is present, which enables us to make REST endpoints.

#### 7.1. Add extension using CLI

So, let's add another extension. We will add the `quarkus-smallrye-openapi` extension, which enables us to generate OpenAPI documentation for our REST endpoints. We will discuss OpenAPI in more detail in the following exercises.

Put the following line to the console and see how Quarkus adds the extension to the application.

```bash
quarkus extension add quarkus-smallrye-openapi
```

Now, you should see the `quarkus-smallrye-openapi` extension in the `pom.xml` file.

Run the application again and go to http://localhost:8079/q/swagger-ui. You should see the OpenAPI documentation for the `GreetingResource` class.

#### 7.2. Other CLI commands related to extensions

- `quarkus extension list` - Lists all installed extensions.
- `quarkus extension remove <extension>` - Removes the extension from the application.

### 8. Submit the solution

1. Finish the tasks
2. Push the changes to the main branch
3. GitHub Classroom automatically prepared a feedback pull request for you
4. GitHub Actions will run basic checks for your submission on push
5. Teacher will evaluate the submission as well and give you feedback

Resubmit the solution if the checks fail:
1. Make changes
2. Push again

## Further reading

- https://quarkus.io
- https://www.baeldung.com/spring-boot-vs-quarkus
- https://quarkus.io/guides/maven-tooling
- https://quarkus.io/guides/dev-mode-differences
- https://quarkus.io/guides/dev-ui
- https://quarkus.io/guides/dev-services#databases
- https://quarkus.io/guides/config
- https://quarkus.io/guides/config-reference
- https://quarkus.io/guides/openapi-swaggerui