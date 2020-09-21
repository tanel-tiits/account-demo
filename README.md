Account Demo Application
------------------------

Java 8 or newer is required for building and running it.

Transaction processing is currently configured to run every 5 seconds.
This can be changed in application.properties (scheduler.cron.expression).

Project comes with Maven Wrapper, no previous Maven installation is required.

~~~bash
# For Windows
mvnw.cmd
~~~

~~~bash
# For OS X and Linux
./mvnw.sh
~~~

### Build application ###

~~~bash
./mvnw.sh clean package
~~~


### Run integration tests ###

~~~bash
./mvnw.sh clean verify
~~~


### Run application ###

Running can be done after building, application runs with in-memory database H2.

~~~bash
./mvnw.sh spring-boot:run
~~~

When application is running various requests can be sent to application with cmd-line scripts in directories (requires curl)

  * misc/requests/valid

  * misc/requests/invalid


### URLs and requests ### 

* Get list of all accounts's data

  http://localhost:8080/api/v1/account


* Get specific account's data

  http://localhost:8080/api/v1/account/NO0312340000001


* Get list of all transactions' data

  http://localhost:8080/api/v1/transaction


* Get specific transaction's data

  http://localhost:8080/api/v1/transaction/19e7c65a-2d84-4e6b-a75d-2c33abf2daee


* Store a new transaction with PUT request

  http://localhost:8080/api/v1/transaction/some-external-uuid

  request body contains the transaction's data:

  ~~~
  {"id":"some-external-uuid","cashAmount":500.0,
   "sourceAccount":"NO0312340000001",
   "destinationAccount":"NO7312340000002"}
  ~~~
  this action is idempotent


* Swagger UI

  http://localhost:8080/swagger-ui.html


* OpenAPI documentation

  http://localhost:8080/api-docs


* Get runtime application info from Actuator

  http://localhost:8080/actuator
