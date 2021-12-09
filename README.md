# SpacX Land GraphQL API Automation testing using Rest Assured with Allure Reports

This project is a demo project which demonstrates Spacex Land GraphQL Launches API automation with Rest Assured and
Java.

Requirement for Test Exercise 1 --  API exercise
---


Using [https://api.spacex.land/graphql/](https://api.spacex.land/graphql/)

- Write a test that queries the API for all launches and returns the number of launches
```shell
* Assert status code is 200
* Assert mission name object is not empty
* Assert that the number of launches is greater than 0
* Assert that that the number of ships is greater than 0
* Assert the first stage & second stage are not null
```
- Write a test to update the ``limit`` parameter and returns the number of launches Ex: ```limit=2```
```shell
* Assert status code is 200
* Assert mission name object is not empty
* Assert that the number of launches is greater than 0
* Assert that that the number of ships is greater than 0
* Assert the first stage & second stage are not null
```
- Write a test to update the ``offset`` parameter and returns the number of launches Ex: ```offset=2```
```shell
* Assert status code is 200
* Assert mission name object is not empty
* Assert that the number of launches is greater than 0
* Assert that that the number of ships is greater than 0
* Assert the first stage & second stage are not null
```

Above are the requirements for the API exercise, use any of the automated testing tools to complete the exercise.

[Rest assured](https://rest-assured.io/)

[Karate](https://github.com/karatelabs/karate)

[Postman](https://www.postman.com/)

Precondition
---
A Java 11 JDK, Maven and an IDE of your choice.

Git Clone to your local repository
---
git clone https://github.com/purushothkrishnaswamy/GlobalTestExercise1.git


Steps
---

* This project is created using IntelijIdea IDE. Once you clone the project navigate to the File-> project structure and
  set the Project SDK as java 11 from your PC
* Install maven and set up the path in OS and update the intelijidea maven settings
* Add Lombok pluign in to the intelijidea by navigating to the File -> Settings -> Plugins and search "Lombok"

Running the tests using Maven
---

```bash
mvn clean test
```

For Allure reports

```bash
mvn clean test allure:report
mvn allure:serve
```
