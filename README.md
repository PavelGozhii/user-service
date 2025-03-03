# Java Test Task

## Overview

The program provides a single REST endpoint for selecting data collected from multiple databases

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- **Java 21**
    - [Download Java](https://adoptopenjdk.net/)
- **Maven** 
    - [Install Maven](https://maven.apache.org/install.html)
## Project Setup

### 1. Clone the Repository

```bash
git clone https://github.com/PavelGozhii/user-service.git
cd user-service
```

### 2. DataSource settings

You can change the DataSources in the application.iml file

```css
└── src
    └── main
        └── resources
            └── application.yml
```

### 3. Build the Project
```bash
mvn clean install
```

