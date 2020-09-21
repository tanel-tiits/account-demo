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


### Requests ### 

* Get list of all accounts

  http://localhost:8080/account


* Get specific account's data

  http://localhost:8080/account/NO0312340000001


* Get list of all transactions

  http://localhost:8080/transaction


* Get specific transaction's data

  http://localhost:8080/transaction/19e7c65a-2d84-4e6b-a75d-2c33abf2daee


* Storing a new transaction is done with PUT request to:

  http://localhost:8080/transaction/some-external-uuid

  with request body containing the transaction's details:

  ~~~
  {"id":"some-external-uuid","cashAmount":500.0,
   "sourceAccount":"NO0312340000001",
   "destinationAccount":"NO7312340000002"}
  ~~~
  action is idempotent


* Actuator - runtime application info

  http://localhost:8080/actuator
