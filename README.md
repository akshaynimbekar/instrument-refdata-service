📊 Instrument Reference Data Service
  A Spring Boot application that manages reference data for financial instruments (ISIN, CUSIP, ticker, exchange, currency).  
  This project is designed to demonstrate enterprise-grade architecture and will later evolve into a Microservices-based system with Kafka, Docker, Kubernetes, and CI/CD pipelines.  

---
 🚀 Features
- ✅ CRUD REST APIs for instrument data
- ✅ PostgreSQL integration with Spring Data JPA
- ✅ Repository–Service–Controller layered architecture
- ✅ Entity auditing (created/updated timestamps)
- ✅ Lombok for boilerplate code reduction
- ⚡ CSV bulk upload support (upcoming)
- ⚡ Kafka event publishing (`instrument.updated`) (upcoming)
- ⚡ Deployed on Docker & Kubernetes (planned)
- ⚡ GitHub Actions CI/CD pipeline (planned)

---
 🛠️ Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven
- Docker (planned)
- Kubernetes (Minikube/EKS) (planned)
- Kafka (planned)

---
 📂 Project Structure

instrument-refdata-service
└── src/main/java/com/shak/refdata
├── controller      REST controllers (API layer)
├── service         Business logic
├── repository      Spring Data JPA repositories
├── entity          JPA entities
└── RefdataApplication.java   Spring Boot main class

---
🔧 Getting Started
 
 1️. Clone the repo
```bash
git clone git@github.com:akshaynimbekar/instrument-refdata-service.git
cd instrument-refdata-service
```

 2️. Configure Database
Create a Postgres DB:

```sql
CREATE DATABASE refdata;
```
Update `src/main/resources/application.yaml` with your DB username/password.

 3️. Run the application
```bash
./mvnw spring-boot:run
```

The service will be available at:
👉 `http://localhost:8080/api/instruments`

---
📌 API Endpoints

 Create Instrument

```http
POST /api/instruments
Content-Type: application/json
```

```json
{
  "isin": "US1234567890",
  "cusip": "123456789",
  "ticker": "AAPL",
  "exchange": "NASDAQ",
  "currency": "USD"
}
```

 Get All Instruments

```http
GET /api/instruments
```

 Find by ISIN

```http
GET /api/instruments/isin/{isin}
```

---
📅 Roadmap

 [x] Project setup with Spring Boot + PostgreSQL
 [x] CRUD APIs for instrument reference data
 [ ] CSV bulk upload
 [ ] Kafka event publishing
 [ ] Docker containerization
 [ ] Kubernetes deployment (Minikube → AWS EKS)
 [ ] GitHub Actions CI/CD

---
👨‍💻 Author

Akshay Nimbekar
🔗 [LinkedIn](https://linkedin.com/in/your-profile) | [GitHub](https://github.com/akshaynimbekar)

***
⭐ If you like this project, don’t forget to star the repo!
***
