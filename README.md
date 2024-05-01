# Ximple Challenge
## Pre-requisites
* java 21
* maven 3.9.6
* docker
* docker compose

## Installation
Next instructions were validated in the following environments.
Vagrant Ubuntu 22.04
Debian 11

### Database container creation
from root of project run:
``cd docker/database && docker compose up -d``
or navigate to ``docker/database`` from the root of database directory run ``docker compose -up``

## Run the project
from root of project run:
``mvn spring-boot:run``
### Endpoints
The endpoints are available on ``http://localhost:8282/ximple``
#### Books
* Get available books ``http://localhost:8282/ximple/books?page=0&size=10``
    * curl code ``curl --location 'http://localhost:8282/ximple/books?page=0&size=10'``
#### Reservations
* Create a reservation ``http://localhost:8282/ximple/reservations/books/:bookId/users/:userId``
    * curl code ``curl --location --request POST 'http://localhost:8282/ximple/reservations/books/3/users/1'``
#### Reviews
* Create a review ``http://localhost:8282/ximple/reviews/books/:bookId/users/:userId``
    * curl code ``curl --location 'http://localhost:8282/ximple/reviews/books/4/users/3' \
--header 'Content-Type: application/json' \
--data '{
    "review":"It is a good book"
}'``
* Get all reviews ``http://localhost:8282/ximple/reviews?size=10&page=0``
    * curl code ``curl --location 'http://localhost:8282/ximple/reviews?size=10&page=0'``
* Get all reviews by book id ``http://localhost:8282/ximple/reviews/books/:bookId?size=10&page=0``
    * curl code ``curl --location 'http://localhost:8282/ximple/reviews/books/4?size=10&page=0'``

**Important Notes**
* run the project after database container is running
* be sure that properties file uses 'beta' profile
* if you are going to run ``mvn clean`` skip test adding``-Dmaven.test.skip=true``
* Do not run tests, that is only for dev phase and need another database with other configuration

## Observability
The project expose enpoints to check health of the application:
``http://localhost:8282/ximple/actuator``
It is ready to integrate with grafana and prometheus with the following url.
``http://localhost:8282/ximple/actuator/prometheus``
Sorry I didn't have time to add containers of grafana and prometheus in order to display metrics in a dashboard.

## Design choices and technologies
* Java 21
* Maven
* Postgres
* Spring
* Springboot

In general there are three layers, the first is a repository layer to persist and recover data from database, the second is a layer of service to implement the bussines logic and the third is a layer to expose the endpoints. The project also contains suit of test in order have quaitly in the code.

### Repository layer
It persists and recovers data from database and laverages features of query methods

### Service Layer
It contains bussines logic, there are 4 services that contains methods to perform operations required by description.
ValidationService is a common service that helps to validates some basic data before doing a specific task.
In accordance with the simplicity of requirements, it doesn't justify  interfaces creation, I tried to not do over-engineering.

### Web Layer
It exposees controllers to perform actions required. Additional to that it adds logic to handle the exception and return the appropieted http code status.

## Database
There are two basics entities into the database, These entities (UserAccount and Book) doesn't stores relationship with any other.
Book has a property called ``available`` that doesn't allow to make a reservation on a book if it is not available.
Reservation entity keeps the relationship with books and user account, it allows to have a history of reservation, the idea is that the same user can make a reservation on a same book several times only if the book is available.
Review entity allows a user to create several reviews over the same book.
