# Libry - SBE Message Encoding Project

Libry is a Java project created as an SBE (Simple Binary Encoding) study object focused on understanding high-performance binary serialization and message processing.

The project demonstrates encoding and decoding workflows using Maven, automated tests, and schema-based message generation.

It provides a structured environment for experimenting with SBE codecs, validating schemas, and testing binary communication concepts.

---

# Features

- Generates SBE codecs from XML schemas
- Encodes and decodes binary messages
- Uses Maven for dependency management and build automation
- Includes automated tests with JUnit 5
- Supports schema validation with XSD
- Organized project structure for SBE learning and experimentation

---

# Installation

## Clone the repository

```bash
git clone <REPOSITORY_URL>
cd Libry
```

## Generate SBE sources

```bash
mvn generate-sources
```

## Run tests

```bash
mvn test
```

---

# Project Structure

```bash
Libry/
 ├── src/
 │   └── test/
 ├── pom.xml
 ├── libry-messages.xml
 ├── sbe.xsd
 └── .github/workflows/
```

---

# Main Files

- `pom.xml` → Maven dependencies and plugins configuration
- `libry-messages.xml` → SBE message schema definitions
- `sbe.xsd` → XML schema validation file
- `src/test` → automated unit tests

---

# Technologies

- Java 17
- Maven
- SBE (Simple Binary Encoding)
- Agrona
- JUnit 5

---

# Contributing

Contributions are welcome.  
If you find bugs, have suggestions, or want to improve the project, feel free to open an issue or submit a pull request.

---

# About SBE

SBE (Simple Binary Encoding) is a binary serialization format designed for low latency and high throughput systems.  
It is commonly used in financial systems, real-time communication, and performance-critical applications.
