# 🛒 Ecommerce API – Microservices Architecture

[![Java](https://img.shields.io/badge/Java-17-blue?logo=java&logoColor=white)](https://www.oracle.com/java/)  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)  
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.x-green?logo=spring&logoColor=white)](https://spring.io/projects/spring-cloud)  
[![Docker](https://img.shields.io/badge/Docker-Containerized-blue?logo=docker&logoColor=white)](https://www.docker.com/)  
[![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?logo=mysql&logoColor=white)](https://www.mysql.com/)

A **microservices-based e-commerce platform** built using **Spring Boot, Spring Cloud, Eureka, Config Server, and API Gateway**.  
The project demonstrates modular, scalable, and fault-tolerant architecture for modern e-commerce applications.

---

## 🚀 Features

- **Centralized Configuration** using Spring Cloud Config Server
- **API Gateway** for unified routing and request forwarding
- **Service Discovery** with Eureka
- **Dedicated Microservices** for Shop, Inventory, and Wallet
- **Docker Support** for containerized deployments
- **REST APIs** for front-end or external integrations
- Authentication & Security _(Planned with JWT)_
- **MySQL Integration** for persistent storage

---

## 🛠 Tech Stack

- **Language:** Java 17+
- **Frameworks:** Spring Boot, Spring Cloud
- **Service Discovery:** Eureka
- **API Gateway:** Spring Cloud Gateway
- **Config Server:** Spring Cloud Config
- **Database:** MySQL
- **Containerization:** Docker, Docker Compose
- **Build Tool:** Maven

---

## 🏗️ Architecture
```text
            +---------------------+
            |  Config Server      |
            +---------------------+
                     │
                     ▼
            +---------------------+
            |    Naming Server    |
            +---------------------+
                     ▲
                     │ Service Discovery + Load Balancing
           +---------------------+
           |    API Gateway      |
           +---------------------+
         /          |          \
        /           |           \
       ▼            ▼            ▼
+----------------+  +----------------+  +----------------+
|  Shop Service  |  | Inventory Svc  |  | Wallet Service |
+----------------+  +----------------+  +----------------+
```

---

## ⚙️ Prerequisites

- Java JDK 17+
- Maven 3.6+
- Git
- Docker (optional for containerization)

---

## ▶️ Running Locally

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/Honda1010/Ecommerce_API.git
cd Ecommerce_API
```

### 2️⃣ Configure Database & Ports

Edit application.properties or YAML files for DB credentials & custom ports.

### 3️⃣ Start Infrastructure Services

```bash
# Start Config Server
cd spring-cloud-config-server
mvn spring-boot:run

# Start Eureka Server
cd ../Naming-server
mvn spring-boot:run

# Start API Gateway
cd ../API-Gateway
mvn spring-boot:run
```

### 4️⃣ Start Business Services

```bash
cd ../ShopMicroService
mvn spring-boot:run

cd ../InventoryMicroService
mvn spring-boot:run

cd ../WalletMicroServices
mvn spring-boot:run
```

## 📊 API Documentation (Swagger/OpenAPI)

[Wallet Service](http://localhost:8080/swagger-ui/index.html#/)

[Inventory Service](http://localhost:8800/swagger-ui/index.html#/)

[Shop Service](http://localhost:8081/swagger-ui/index.html#/)

---

## 🔌 Default Ports

| Service           | Default Port | Purpose                             |
| ----------------- | ------------ | ----------------------------------- |
| Naming Server     | 8761         | Service Registry (Eureka Dashboard) |
| Config Server     | 8888         | Configuration Management            |
| API Gateway       | 8765         | Main Application Entry Point        |
| Wallet Service    | 8080         | Payment Processing                  |
| Inventory Service | 8800         | Inventory Management                |
| Shop Service      | 8081         | Shopping Operations                 |

---

## 📄 License

This project is licensed under the **MIT License** – feel free to use, modify, and distribute.
