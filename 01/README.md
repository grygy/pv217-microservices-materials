# 01 - Introduction to Quarkus

## What is Quarkus?

Quarkus is an open-source Kubernetes-native Java framework tailored for GraalVM and HotSpot, crafted from best-of-breed Java  libraries and standards. The goal is to make Java the leading platform in Kubernetes and serverless environments while offering developers a framework to address a wider range of distributed application architectures.

## How is Quarkus different to Spring boot

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

## Quarkus Technologies

- **Maven** to create a new project, add or remove extensions, launch development mode, debug your application, and build your application.
- **Jakarta EE** to write your business logic.
- **MicroProfile** to write your REST endpoints, inject configuration, and more.

## Tasks

This week's lecture will be more about reading and understanding the Quarkus developer experience than about coding. We will create a simple Quarkus application and run it in development mode.


### 0. Prerequisites

Install Java 17

1. Check java version
    ```bash
    java --version
   # should be 17
    ```
   
2. Install OpenJDK 17
    Find the latest version of OpenJDK 17 [here](https://jdk.java.net/17/).

3. Check java version
    ```bash
    java --version
   # should be 17
    ```

4. Install Maven
    
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
5. Verify Maven installation
    ```bash
    mvn --version
    ```

### 1. Install Quarkus CLI

Even though Quarkus application can be created using Maven, Quarkus CLI is a convenient tool to create and manage Quarkus applications. 

1. Install Quarkus CLI 
   - You can find more installation options [here](https://quarkus.io/guides/cli-tooling).
      ```bash
      # linux SKDMAN!
      sdk install quarkus
   
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
    
   Let's use `quarkus create app <groupID>:<artifactID>` to create a Quarkus application. We will add the `resteasy-reactive-jackson` extension to support REST endpoints.
   - `groupID` is the package name of the application. It is usually the reverse domain name of the organization.
   - `artifactID` is the name of the application (and .jar file).
    ```bash
    quarkus create app cz.muni.fi:flight-service --extension='resteasy-reactive-jackson'
    ```
   
    Open the project in IntelliJ IDEA or your favorite IDE. 
   
    The folder `flight-service` should be created. It contains the following files:
    - `pom.xml` - Maven project file (name, version, dependencies, plugins, ...)
    - `src/main/resources/application.properties` - application configuration
    - `src/main/java/cz/muni/fi//GreetingResource.java` - REST endpoint
    - `src/test/java/cz/muni/fi//GreetingResourceTest.java` - REST endpoint unit test
    - `src/test/java/cz/muni/fi//GreetingResourceIT.java` - REST endpoint integration test


   

   
    

TODO

## Further reading
- https://quarkus.io
- https://www.baeldung.com/spring-boot-vs-quarkus
- https://quarkus.io/guides/maven-tooling
