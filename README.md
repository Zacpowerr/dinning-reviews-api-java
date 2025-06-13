# Dinning Reviews API (Java Spring Boot)

A RESTful API for managing users, restaurants, and allergy-aware dining reviews. Built with Java 17, Spring Boot, JPA, and H2 database. This project demonstrates a robust backend architecture for a review platform, focusing on food allergies.

## Features

- **User Management**: Create, update, delete, and search users with allergy profiles.
- **Restaurant Management**: Add, update, delete, and search restaurants by zip code.
- **Review Management**: Submit, update, delete, and search reviews for restaurants, including allergy-specific scores.
- **Admin Review Approval**: Admin endpoints for approving or denying reviews.
- **Allergy Awareness**: Track and aggregate allergy-specific scores for restaurants.
- **In-memory H2 Database**: Easy setup for development and testing.

## Technologies

- Java 17
- Spring Boot 3.5.x
- Spring Data JPA
- H2 Database
- Maven

## Getting Started

### Prerequisites

- Java 17+
- Maven

### Running the Application

```sh
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

### Database

The API uses an H2 file-based database stored at `./data/database.mv.db`. Schema is auto-generated and updated on startup.

## API Endpoints

### User Endpoints

| Method | Endpoint                     | Description                   |
| ------ | ---------------------------- | ----------------------------- |
| GET    | `/users`                     | List all users                |
| POST   | `/users`                     | Create a new user             |
| PUT    | `/users/{username}`          | Update user details           |
| DELETE | `/users/{username}`          | Delete a user                 |
| GET    | `/users/search?username=...` | Search for a user by username |
| GET    | `/users/{username}/reviews`  | Get all reviews by a user     |

#### Example User JSON

```json
{
  "userName": "john",
  "city": "NYC",
  "state": "NY",
  "zipcode": "10001",
  "hasDairyAllergy": true,
  "hasEggAllergy": false,
  "hasPeanutAllergy": true
}
```

---

### Restaurant Endpoints

| Method | Endpoint                                          | Description                                                                            |
| ------ | ------------------------------------------------- | -------------------------------------------------------------------------------------- |
| GET    | `/restaurants`                                    | List all restaurants                                                                   |
| POST   | `/restaurants`                                    | Create a new restaurant                                                                |
| PUT    | `/restaurants/{id}`                               | Update restaurant details                                                              |
| DELETE | `/restaurants/{id}`                               | Delete a restaurant                                                                    |
| GET    | `/restaurants/search?zipCode=...&allergyType=...` | Search restaurants by zip code and sort by allergy score (`egg`, `dairy`, or `peanut`) |
| GET    | `/restaurants/{id}/reviews`                       | Get all approved reviews for a restaurant                                              |

#### Example Restaurant JSON

```json
{
  "name": "Pizza Place",
  "zipCode": "10001"
}
```

---

### Review Endpoints

| Method | Endpoint                           | Description                     |
| ------ | ---------------------------------- | ------------------------------- |
| GET    | `/reviews`                         | List all reviews                |
| POST   | `/reviews`                         | Create a new review             |
| PUT    | `/reviews/{id}`                    | Update a review                 |
| DELETE | `/reviews/{id}`                    | Delete a review                 |
| GET    | `/reviews/search?restaurantId=...` | Search reviews by restaurant ID |

#### Example Review JSON

```json
{
  "userName": "john",
  "restaurantId": 1,
  "peanutScore": 5,
  "eggScore": 4,
  "diaryScore": 3,
  "details": "Great!"
}
```

---

### Admin Endpoints

| Method | Endpoint                    | Description                       |
| ------ | --------------------------- | --------------------------------- |
| GET    | `/admin`                    | List all reviews pending approval |
| POST   | `/admin/{reviewId}/approve` | Approve a review                  |
| POST   | `/admin/{reviewId}/deny`    | Deny a review                     |

---

## Error Handling

- Returns appropriate HTTP status codes (`404`, `409`, etc.) and messages for not found, conflict, or invalid requests.

## Example Usage

See [`test commands.txt`](test%20commands.txt) for example `curl` commands to interact with the API.

---

## Project Structure

- `src/main/java/com/eduardobastos/dinningreviews/models` - Entity models
- `src/main/java/com/eduardobastos/dinningreviews/repositories` - Spring Data repositories
- `src/main/java/com/eduardobastos/dinningreviews/controllers` - REST controllers
- `src/test/java/com/eduardobastos/dinningreviews` - Unit tests

---

## Running Tests

```sh
./mvnw test
```

---

## License

This project is for portfolio and educational purposes.

---

## Contact

Eduardo Bastos  
[LinkedIn](https://www.linkedin.com/in/eduardokbastos/)
