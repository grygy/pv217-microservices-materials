# 04 - Persistence

## Persistence with Panache

Panache is ORM (Object Relational Mapping) layer for Quarkus. It is based on Hibernate ORM and Hibernate Reactive.

For both PanacheEntity and PanacheRepository, you can see examples bellow.

### What is ORM?

ORM is a technique that lets you query and manipulate data from a database using an object-oriented paradigm. Thus, instead of writing SQL queries, you can write Java code to perform the same operations.

It introduces abstraction between the database and the application. You can change more easily the database without changing the application code.


### PanacheEntity

PanacheEntity is a base class for entities. It provides basic operations for entities such as persist, delete, find, etc. It's used for active record pattern.

#### What gives you PanacheEntity?

Attributes:
- `id` - Automatically adds the primary key of the entity.

Methods:
- `persist()` - Persists the entity to the database.
- `delete()` - Deletes the entity from the database.
- `isPersistent()` - Checks if the entity is persistent.
- `findById()` - Finds an entity by its primary key.
- `listAll()` - Returns a list of all entities.
- `count()` - Returns the number of entities.
- `find()` - Finds entities by a query.
- ... and more

### PanacheRepository

PanacheRepository is a base class for repositories. It provides similar logic as PanacheEntity, but it's used for repository pattern.

But you need to define the entity more explicitly. With id, getters and setters, etc. Then you will create a repository class that extends `PanacheRepository<Entity>`.

### `@Transactional` annotation

`@Transactional` annotation is used to mark a method as transactional. It means that the method will be executed in a transactional context. If the method fails, the transaction will be rolled back.

## Asynchronous Mutiny (side topic)

Since we will be using reactive approach it is a good thing to know something about Mutiny.

Mutiny is a reactive programming library for Java. It is based on Reactive Streams and Reactive Streams Operators. So when you see `Uni` or `Multi` in the code, it means that the method returns a reactive type and the method is asynchronous. 

**What `Uni` and `Multi` are?**

`Uni` is a type that emits either a single item or an error. `Multi` is a type that emits a stream of items or an error.

**Pros of asynchronous programming:**
- Non-blocking
- Better resource utilization
- Better scalability

**Cons of asynchronous programming:**
- Complexity of callbacks, exceptions
- Harder to debug

For more in depth discussion about asynchronous programming and the difference with multithreading, see [this article](https://www.baeldung.com/cs/async-vs-multi-threading). 

### `onItem` and `transform` methods

TODO

[//]: # (TODO)

### Example

TODO

[//]: # (TODO)

## Active record vs repository

Both active record and repository are patterns for accessing data in a database.

### Active record

The Active record pattern is an approach where the data access logic is part of the entity itself. Each entity (or record) is responsible for its own persistence and encapsulates both the data and the behavior that operates on the data.

Pros:
- It's easy to set up for simple operations
- Less boilerplate

Cons:
- Logic not separated from data
- Tight coupling between schema and code
- Hard to test

#### Example

```java
@Entity
public class Person extends PanacheEntity {
    public String name;
    public LocalDate birth;
    public Status status;

    public static Uni<Person> findByName(String name){
        return find("name", name).firstResult();
    }
}
```
Basic usage
```java    
// persist it
Uni<Void> persistOperation = person.persist();

// check if it is persistent
if(person.isPersistent()){
    // delete it
    Uni<Void> deleteOperation = person.delete();
}

// getting a list of all Person entities
Uni<List<Person>> allPersons = Person.listAll();

// finding a specific person by ID
Uni<Person> personById = Person.findById(23L);
```

### Repository

Instead of having both schema and logic in the same class, the repository pattern separates schema from data access logic in a separate class. 

Pros:
- Logic separated from data
- Cleaner and more testable code
- DAL (Data Access Layer) is decoupled from the rest of the application

Cons:
- More boilerplate for simple operations
- Takes more time to set up

#### Example

```java
@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDate birth;
    private Status status;
    
    // getters and setters
}

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {

    // put your custom logic here as instance methods

    public Uni<Person> findByName(String name){
        return find("name", name).firstResult();
    }

    public Uni<List<Person>> findAlive(){
        return list("status", Status.Alive);
    }

    public Uni<Long> deleteStefs(){
        return delete("name", "Stef");
    }
}
```

#### `@WithTransaction` annotation

- `@WithTransaction` annotation is used to mark a method as transactional. It means that the method will be executed in a transactional context. If the method fails, the transaction will be rolled back. This annotation is used if the method changes the state of the database. So it's not necessary to use it for read-only methods.
- Transactions follow unit of work pattern. It means that all operations in a transaction are treated as a single unit of work. If any operation fails, the whole transaction is rolled back.
- Used primarily to annotate methods.

#### `@WithSessino` annotation // TODO MAYBE DELETE

- `@WithSession` session can span multiple transactions. Ensures that entities withing the same session are consistent.
- Used primarily to annotate repositories.

## dev services explanation

[//]: # (TODO docker in dev mode etc.)
TODO

## State of the project

- The `flight-service` has implemented the repository pattern with panache.
- REST APIs and services are now asynchronous. 
- Objects DTOs are created for the communication between services.

## Tasks

### 0. Running docker

Install [Docker desktop](https://docs.docker.com/desktop/) or other docker client. Our test database will run in docker container.

### 1. Configure datasource in `application.properties`

Or maybe not coz dev services

### 2. Create passenger entity

### 2. Add notification as active record

### 3. Add passenger as repository

### X. Submit the solution

[//]: # (TODO after setting up github classroom)

## Hints

- In `flight-service` you can find implemented repository pattern with panache.
- If something is not working, and it should (Developers aren't doing mistakes right?) run maven clean and compile commands.

## Troubleshooting

- Check if your docker engine is running.

## Further reading

- https://quarkus.io
- https://quarkus.io/guides/hibernate-reactive-panache
- https://medium.com/@shiiyan/active-record-pattern-vs-repository-pattern-making-the-right-choice-f36d8deece94
- https://quarkus.io/guides/mutiny-primer