## Introduction

Gherkin-Style logger used for automated testing of TestNG-based automation solutions.  This service enables users to 
decorate their TestNG logs with [Behavior Driven Development/Gherkin style](https://code.google.com/p/spectacular/wiki/WritingBDDTests) 
logging.  The service includes static methods to quickly and easily decorate your logs as well as your TestNG Reports.  Furthermore, 
each log message prints the exact timestamp in which an event occurred. 
   
Also, this service automatically formats the all logging message strings and its arguments by ultimately using the String.format() 
capabilities of the String object.  Many of the methods in this API use Java's variable arguments. 

## Prerequisites

1. Java 1.7
2. Maven 3.x

## Maven Dependency

```
<dependency>
    <groupId>com.pacificwebconsulting.logging</groupId>
    <artifactId>logging-microservice</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Integration

Simply, integrate the **LoggerService** service into your project by adding the following static import statement to your class:

```
import static com.pwc.logging.service.LoggerService.*;
```

## Usage
* LOG();
* FEATURE();
* SCENARIO();
* GIVEN();
* WHEN();
* THEN();
* AND();
* BUT();

## Output

```
Feature:  General Security
Scenario:  Deny a guest access to restricted material
   Given I am an unauthenticated guest
   When I attempt to access restricted content
   Then I am denied access to the restricted content
   And I verify the message
   But I see an invalid title
```


## Tips & Tricks
[Declarative vs. Imperative Logging](http://itsadeliverything.com/declarative-vs-imperative-gherkin-scenarios-for-cucumber)
