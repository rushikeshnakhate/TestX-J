# Spring Boot Cucumber Test Framework with Solace Integration

This project provides a Spring Boot-based Cucumber test framework for testing Solace messaging operations using the JCSMP library. The framework follows SOLID principles and uses modern Java features like records.

## Features

- **Spring Boot Integration**: Full Spring Boot support with dependency injection
- **Java Records**: Modern Java features for data classes
- **Multiple Message Types**: Support for Text, JSON, Protocol Buffers, and Binary messages
- **Strategy Pattern**: Message handlers using strategy pattern for extensibility
- **SOLID Principles**: Clean, maintainable code following SOLID principles
- **Modular Design**: Small, focused classes with single responsibilities
- **Dedicated Step Files**: Separate step definition files for different concerns

## Project Structure

```
src/
├── main/
│   ├── java/org/example/
│   │   ├── config/
│   │   │   ├── SolaceConfig.java          # Spring configuration
│   │   │   └── SolaceProperties.java      # Configuration properties (record)
│   │   ├── message/
│   │   │   ├── Message.java               # Base message interface
│   │   │   ├── TextMessage.java           # Text message (record)
│   │   │   ├── JsonMessage.java           # JSON message (record)
│   │   │   ├── ProtobufMessage.java       # Protobuf message (record)
│   │   │   └── BinaryMessage.java         # Binary message (record)
│   │   ├── handler/
│   │   │   ├── MessageHandler.java        # Handler interface
│   │   │   ├── TextMessageHandler.java    # Text handler
│   │   │   ├── JsonMessageHandler.java    # JSON handler
│   │   │   ├── ProtobufMessageHandler.java # Protobuf handler
│   │   │   └── MessageHandlerFactory.java # Handler factory
│   │   ├── solace/
│   │   │   ├── SolaceConnection.java      # Connection management
│   │   │   ├── SolaceProducer.java        # Message producer
│   │   │   └── SolaceConsumer.java        # Message consumer
│   │   └── TestApplication.java           # Spring Boot application
│   └── resources/
│       ├── application.properties         # Configuration
│       └── logback.xml                    # Logging configuration
└── test/
    ├── java/org/example/
    │   ├── steps/
    │   │   ├── ConnectionSteps.java       # Connection steps
    │   │   ├── MessageSteps.java          # Message steps
    │   │   └── ValidationSteps.java       # Validation steps
    │   ├── config/
    │   │   └── TestConfiguration.java     # Test configuration
    │   ├── CucumberTestRunner.java        # JUnit 5 test runner
    │   └── SolaceTestRunner.java          # JUnit 4 test runner
    └── resources/features/
        ├── connection_test.feature        # Connection tests
        └── message_test.feature           # Message tests
```

## Key Design Patterns

### 1. Strategy Pattern
- `MessageHandler` interface with different implementations
- `MessageHandlerFactory` for handler selection
- Easy to extend with new message types

### 2. Factory Pattern
- `MessageHandlerFactory` creates appropriate handlers
- `SolaceConnection` manages connection creation

### 3. Dependency Injection
- Spring Boot manages all dependencies
- Easy testing with mock objects
- Configuration through properties

## Message Types

### Text Messages
```java
Message textMessage = new TextMessage("Hello World");
```

### JSON Messages
```java
Message jsonMessage = new JsonMessage(Map.of("id", 123, "name", "Test"));
```

### Protocol Buffer Messages
```java
Message protobufMessage = new ProtobufMessage(testMessage);
```

### Binary Messages
```java
Message binaryMessage = new BinaryMessage(bytes);
```

## Configuration

Update `application.properties`:

```properties
solace.host=tcp://your-solace-host:55555
solace.username=your-username
solace.password=your-password
solace.vpn=your-vpn-name
solace.client-name=TestClient
```

## Running Tests

```bash
# Run all tests
mvn test

# Run specific test runner
mvn test -Dtest=CucumberTestRunner
```

## Example Test

```gherkin
Feature: Message Publishing and Consuming
  Scenario: Publish and consume text message
    Given I have a Solace connection
    And I connect to Solace
    And I have a text message "Hello World"
    And I start consuming from topic "test/topic"
    When I publish the message to topic "test/topic"
    And I wait for messages for 5 seconds
    Then I should receive 1 message(s)
    And the received message should contain "Hello World"
    And the message should be of type "TEXT"
```

## Benefits of This Design

1. **Small Classes**: Each class has a single responsibility
2. **Easy Testing**: Spring Boot makes mocking and testing simple
3. **Extensible**: Easy to add new message types and handlers
4. **Maintainable**: Clear separation of concerns
5. **Modern Java**: Uses records and modern Java features
6. **SOLID Principles**: Follows all SOLID principles

## Adding New Message Types

1. Create a new message class implementing `Message`
2. Create a handler implementing `MessageHandler`
3. Register the handler as a Spring component
4. The factory will automatically pick it up

## Adding New Step Definitions

1. Create a new step definition class
2. Use `@Autowired` for dependency injection
3. Add the package to the test runner glue configuration

This design provides a clean, maintainable, and extensible framework for testing Solace messaging operations.
