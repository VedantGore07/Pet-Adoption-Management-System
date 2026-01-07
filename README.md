🐾 Pet Adoption Management System
📌 Overview

The Pet Adoption Management System is a microservices-based solution designed to streamline the operations of animal shelters and adoption agencies by digitizing and automating key processes. It manages pet profiles, adopter details, adoption workflows, medical and vaccination records, and volunteer contributions.

The solution ensures compliance with adoption and health regulations, trans'parency in operations, reduced manual errors, and an improved adoption experience for both adopters and shelter staff.

🧰 Technology Stack
Category	Technology
Architecture	Microservices
Registry	Eureka Server
API Gateway	Spring Cloud Gateway
Backend	Java, Spring Boot
Service-to-Service Communication	Feign Client
Frontend	Thymeleaf
Database	MySQL
Security	Security Service (Spring Security-based centralized authentication and authorization)
Cross-Cutting Concerns	Global Exception Handling, Validation Service
🧩 Microservices & Responsibilities
1️⃣ Pet Service

Register and manage pet profiles (Pet ID, name, type, breed, age, health status, vaccination status, adoption status).

Update pet availability.

Volunteers can upload pet photos and daily observations.

Integrates with Medical & Vaccination Service for health records.

Access controlled via Security Service (only authorized roles can update or register pets).

2️⃣ Adopter Service

Register adopters with unique Adopter ID.

Manage adopter details (name, address, contact, preferences).

Update or deactivate adopter profiles.

Integrates with Adoption Service to track requests and approvals.

Communicates with Security Service for role-based access and authentication.

3️⃣ Adoption Service

Handle adoption requests from adopters.

Validate adopter eligibility and pet availability.

Approve or reject adoption requests.

Volunteers assist adopters during adoption drives.

Integrations:

Validation Service → eligibility checks.

Medical & Vaccination Service → ensure pets are medically cleared before approval.

Security Service → verify requester's role and permissions (Admin approval, Adopter request submission).

4️⃣ Medical & Vaccination Service

Maintain medical and vaccination records for pets.

Enforce vaccination rules (pets must be vaccinated before adoption).

Send reminders for upcoming/due vaccinations.

Generate pet health certificates for adopters.

Integrations:

Adoption Service (adoption approval checks).

Validation Service (rule enforcement).

Security Service (authentication for medical staff and vets).

5️⃣ Validation Service

Centralized service for enforcing business rules across microservices.

Validations include:

Pet data: valid age, type, vaccination status.

Adopter data: unique contact info, active status.

Adoption rules: max pets, duplicate requests.

Medical rules: vaccination schedules, health clearances.

6️⃣ Security Service

Centralized Authentication and Authorization Microservice using Spring Security and JWT (JSON Web Tokens).

Handles user login, token generation, and role-based access control for all other microservices.

Manages roles and permissions for Admin, Adopter, Staff, Volunteer, and Vet.

Provides authentication endpoints consumed by the API Gateway.

Validates incoming requests using tokens before forwarding them to respective microservices.

Integrates with all other services for access control enforcement.

Responsibilities:

Manage user credentials and roles.

Authenticate users (Admin, Adopter, Staff, Volunteer, Vet).

Authorize API access based on role and permissions.

Integrate with API Gateway for token validation.

Centralize security policies for consistency across services.

🤝 Volunteer Role
Responsibilities

Assist Admin and Staff in adoption events and campaigns.

Update pet photos and daily behavior/feeding observations.

Help adopters with registration during events.

Participate in outreach campaigns and awareness programs.

🎨 Frontend (Thymeleaf) – UI Pages
UI Page	Description	Validations & Integrations
Home Page	Landing page with navigation for Adopters, Admin, Staff, Volunteers.	Security Service → role-based authentication
Register Pet Page	Add new pets with details.	Validation Service → check valid type, age > 0
View Pet Details Page	Search by Pet ID or list all pets.	Fetches vaccination/health info (Medical Service)
Register Adopter Page	Adopter registration form.	Validation Service → unique email/phone, valid address
Update Adopter Page	Modify adopter details/preferences.	Validation Service → contact info validation
Adoption Request Page	Adopter submits adoption request.	Validation Service + Medical Service checks
Adoption Approval Page (Admin)	Admin approves/rejects adoption requests.	Integrates with Medical Service
Vaccination Management Page (Vet/Staff)	Add/update vaccination details.	Validation Service → schedule enforcement
Health Certificate Page	Download/view health certificate.	Medical Service integration
Volunteer Dashboard	Landing page for volunteers.	Security Service → role validation
Assigned Pets Page (Volunteer)	Volunteers view/manage pets assigned to them.	Pet Service integration
Daily Pet Update Page (Volunteer)	Volunteers log daily observations (behavior, feeding).	Validation Service
Event Participation Page (Volunteer)	Register for adoption events.	Event management
🔄 Inter-Microservice Communication Examples

Adoption Service → Validation Service → Verify adopter eligibility and pet availability.

Adoption Service → Medical Service → Ensure pet vaccinations are up-to-date before approval.

Volunteer UI → Pet Service → Update pet photos and daily notes.

API Gateway → Security Service → Validate user authentication and authorization before forwarding requests.

🔐 Security (Now handled by Security Service)

Admin: Full access (pets, adopters, adoptions, medical records).

Adopter: View pets, request adoption, track status, view health certificates.

Vet/Medical Staff: Manage medical & vaccination records.

Volunteer: Limited access (pet updates, event participation, assisting adopters).

Staff: Assist in managing adoption and shelter operations.

Centralized authentication & token-based authorization across all services.

🐳 Pet Adoption Management System – Docker Deployment Guide
1️⃣ Objective

This document provides a high-level approach for deploying the Pet Adoption Management System (microservices-based architecture) using Docker containers. It ensures all microservices, databases, and supporting components run in isolated containers, promoting portability, consistency, and easy scaling.

2️⃣ Pre-Requisites
Infrastructure

Linux or Windows host machine or cloud VM with Docker installed.

Minimum hardware: 8 CPU cores, 16 GB RAM, 100 GB disk.

Software

Docker Engine installed.

Docker Compose (optional, for multi-container orchestration).

Network connectivity to clone the application from Git repository.

Access

Git repository URL and credentials (if private).

Database access credentials (MySQL username, password, database name).

Environment variables for service configurations.

Knowledge

Understanding of microservices architecture.

Familiarity with containerization concepts (Docker images, containers, volumes, networks).

3️⃣ Deployment Architecture

The system consists of multiple microservices communicating via REST APIs and Feign clients, with centralized security and cross-cutting concerns.

Docker Architecture Overview
Containers

Each microservice runs in its own Docker container.

MySQL database runs in a separate container with persistent storage.

Optional: Docker network for container communication.

Networking

Use a custom Docker bridge network to allow microservices to discover and communicate with each other.

Each container exposes only necessary ports externally (internal services communicate via container names).

Persistence

Use Docker volumes for:

MySQL data persistence.

Environment Configuration

Pass configuration via environment variables or external .env files:

Database URL, username, password

Eureka Server URL

Security Service JWT secrets

4️⃣ Deployment Steps (High-Level)
Step 1: Clone Application Repository

Pull the latest code from Git repository for all microservices and frontend.

Step 2: Build Docker Images

Create Docker images for each microservice and frontend application.

Tag images appropriately (e.g., pet-service:latest, adopter-service:latest).

Step 3: Configure Docker Network

Create a dedicated Docker network for all microservices to communicate internally.
Example:

docker network create pet-adoption-network

Step 4: Configure Persistent Volumes

Define Docker volumes for:

MySQL database (mysql-data)

Step 5: Deploy MySQL Container

Deploy MySQL container with:

Exposed port for internal communication (e.g., 3306)

Mounted volume for persistence

Configured credentials via environment variables

Step 6: Deploy Microservices Containers

Deploy each microservice container with:

Environment variables pointing to database and Eureka server.

Links to required services (e.g., Adoption Service → Validation Service, Medical Service).

Role-based environment settings (e.g., security service credentials).

Step 7: Deploy API Gateway

Run API Gateway container to route requests to appropriate microservices.
Connect to Security Service for token-based validation.

Step 8: Deploy Frontend

Deploy Thymeleaf frontend container.
Connect frontend container to API Gateway.

Step 9: Verify Inter-Service Communication

Test microservices communication internally via Docker network.

Ensure services register correctly with Eureka Server.

Step 10: Health Checks

Confirm all microservices are up and running.

Verify database connectivity and correct environment configurations.

Validate user authentication and role-based access via Security Service.


+---------------------------+
|   Browser / UI Layer      |
|   (Thymeleaf Frontend)    |
+-------------+-------------+
              |
              v
+---------------------------+
|      API Gateway          |
|  (Spring Cloud Gateway)   |
+-------------+-------------+
              |
              v
+---------------------------+
|     Security Service      |
| (Spring Security + JWT)   |
+-------------+-------------+
              |
              v
+---------------------------+
|      Eureka Server        |
|  (Service Discovery)     |
+-------------+-------------+
              |
              v
+---------------------------------------------------+
|               Core Microservices                  |
|                                                   |
|  +-----------+   +-----------+   +-------------+ |
|  | Pet       |   | Adopter   |   | Adoption    | |
|  | Service   |   | Service   |   | Service     | |
|  +-----------+   +-----------+   +-------------+ |
|        |                 |              |        |
|        +--------+--------+--------------+        |
|                 |                               |
|      +---------------------------+              |
|      | Medical & Vaccination     |              |
|      | Service                   |              |
|      +---------------------------+              |
|                 |                               |
|      +---------------------------+              |
|      | Validation Service        |              |
|      +---------------------------+              |
+---------------------------------------------------+
              |
              v
+---------------------------+
|        MySQL Database     |
+---------------------------+
