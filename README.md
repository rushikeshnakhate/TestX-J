# Spring Boot Cucumber Test Framework with Solace Integration

This is a single-source Spring Boot application that includes Cucumber test framework for testing Solace messaging operations using the JCSMP library.

## Features

- **Single Source**: No separate test folder - everything is in main source
- **Spring Boot Application**: Can run as a standalone application
- **Cucumber Integration**: BDD testing with feature files
- **Multiple Message Types**: Text, JSON, Protocol Buffers, Binary
- **SOLID Principles**: Clean, maintainable code
- **Java Records**: Modern Java features

## Project Structure

```
src/
├── main/
│   ├── java/org/example/
│   │   ├── config/
│   │   │   ├── SolaceConfig.java          # Spring configuration
│   │   │   ├── SolaceProperties.java      # Configuration properties (record)
│   │   │   └── TestConfiguration.java     # Test configuration
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
│   │   ├── steps/
│   │   │   ├── ConnectionSteps.java       # Connection steps
│   │   │   ├── MessageSteps.java          # Message steps
│   │   │   └── ValidationSteps.java       # Validation steps
│   │   ├── TestApplication.java           # Spring Boot application
│   │   └── TestRunner.java                # Cucumber test runner
│   └── resources/
│       ├── features/
│       │   ├── connection_test.feature    # Connection tests
│       │   └── message_test.feature       # Message tests
│       ├── application.properties         # Configuration
│       └── logback.xml                    # Logging configuration
```

## Configuration

Update `src/main/resources/application.properties`:

```properties
solace.host=tcp://your-solace-host:55555
solace.username=your-username
solace.password=your-password
solace.vpn=your-vpn-name
solace.client-name=TestClient
```

## Running the Application

### 1. Run as Spring Boot Application
```bash
mvn spring-boot:run
```

### 2. Run Cucumber Tests
```bash
mvn exec:java -Dexec.mainClass="org.example.TestRunner"
```

### 3. Build and Run JAR
```bash
mvn clean package
java -jar target/TestX-J-1.0-SNAPSHOT.jar
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

## Message Types

### Text Message
```java
Message textMessage = new TextMessage("Hello World");
```

### JSON Message
```java
Message jsonMessage = new JsonMessage("{\"id\": 123, \"name\": \"Test\"}");
```

### Protocol Buffer Message
```java
Message protobufMessage = new ProtobufMessage(testMessage);
```

### Binary Message
```java
Message binaryMessage = new BinaryMessage(bytes);
```

## Benefits

1. **Single Source**: No test folder - everything in main
2. **Spring Boot**: Can run as standalone application
3. **Simple**: Clean, focused classes
4. **Extensible**: Easy to add new message types
5. **Modern Java**: Uses records and latest features
6. **SOLID Principles**: Follows all SOLID principles

## Adding New Features

1. **New Message Type**: Create new message class implementing `Message`
2. **New Handler**: Create handler implementing `MessageHandler`
3. **New Steps**: Add step definition class in `steps` package
4. **New Features**: Add feature file in `resources/features`

This design provides a clean, maintainable, and extensible framework for testing Solace messaging operations in a single Spring Boot application.