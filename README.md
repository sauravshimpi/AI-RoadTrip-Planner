# AI Road Trip Planner 🚗🌍

An AI-powered road trip planning application that helps users discover places, plan trips, and get intelligent travel suggestions.

## 📌 About the Project

AI Road Trip Planner is a full-stack web application built using Java, Spring Boot, and a modern JavaScript frontend. It combines trip planning, location-based services, and AI-generated suggestions to help users organize their travel plans.

## ✨ Features

* **Trip Planning:** Create travel itineraries based on trip details.
* **Places Search:** Discover places using location and geocoding services.
* **AI-Powered Suggestions:** Generate travel recommendations using Gemini AI integration.
* **User Authentication:** Register and log in to the application.
* **Saved Trips:** Save and access previously planned trips.
* **REST APIs:** Connect the frontend and backend through REST endpoints.

## 🛠️ Tech Stack

**Backend**

* Java
* Spring Boot
* Spring REST APIs
* Maven

**Frontend**

* React
* Vite
* JavaScript
* HTML and CSS

**Database**

* MySQL

**Integrations**

* Google Gemini API
* Geocoding and places services

## 📂 Project Structure

```text
AI-RoadTrip-Planner/
├── src/
│   └── main/
│       ├── java/
│       └── resources/
├── ai-roadtrip-frontend/
│   ├── public/
│   ├── src/
│   ├── package.json
│   └── vite.config.js
├── pom.xml
└── README.md
```

## ⚙️ Prerequisites

Install the following before running the project:

* Java JDK compatible with the project's configuration
* Maven, or use the included Maven Wrapper if available
* Node.js and npm
* MySQL Server

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/sauravshimpi/AI-RoadTrip-Planner.git
cd AI-RoadTrip-Planner
```

### 2. Configure the database

* Start your MySQL server.
* Create the database required by the application.
* Configure the database connection in your local application configuration.
* Provide database credentials through environment variables or another secure local configuration method.

Use the database name and settings expected by your backend code.

### 3. Configure environment variables

Set the required configuration for your database, Gemini API, and other external services.

Do not commit real API keys, passwords, or private credentials to GitHub.

### 4. Run the backend

From the project root, run:

```bash
mvn spring-boot:run
```

The backend will start on the port configured in your application settings.

### 5. Run the frontend

Open a second terminal:

```bash
cd ai-roadtrip-frontend
npm install
npm run dev
```

Open the local URL printed by Vite in your terminal.

Make sure the frontend API configuration points to the running backend.

## 🔐 Security

* Keep API keys and database credentials private.
* Use environment variables for sensitive configuration.
* Never commit `.env` files containing secrets.
* Configure appropriate authentication and CORS settings before deployment.

## 🔮 Future Improvements

* Deploy the frontend and backend online.
* Improve trip itinerary customization.
* Add automated testing for REST APIs.
* Enhance the user experience and error handling.

## 👨‍💻 Developer

**Saurav Shimpi**

GitHub: [@sauravshimpi](https://github.com/sauravshimpi)

---

If you find this project interesting, feel free to explore the repository and its implementation.
