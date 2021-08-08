# Job Management System
This project was created to handle different types of jobs. 

## Implementation & Assumptions
* ### Event Driven Design
  In this Design Pattern, the publishers to publish scheduled job events to listeners to trigger event
  execute them accordingly in parallel manners based on active available thread on each thread wall. This makes events lousily coupled and maintainable 
* ### Priorities Handling
   I assumed that we have three priorities, High, Medium, Low.
If a jobs is executed in the background has same execution time, then job with higher priority is executed.
* ### Scheduling
   Event publisher publishes jobs as events to listeners based on execution time. Upon job creation the default state 
is queued, if the execution time is immediately, 
then the job state changes to running and is published by event publisher. After creation based on the  output 
of the executed job either it changes the state to Successful or Failed.  
* ### Reliability 
  Each job is handled that it never effect the system if it fails, an 
exception is handled to change the job state and log the exception message 
into job notes.
* ### Execution
    At the start of the project, I create 300 Jobs with different 
types, and execution times, just to simulate the project work. These jobs output can be viewed in the logs. 
And Job APIs can be used to fetch them. The user
still has the ability to create another jobs of type Reminder or 
Email and in the future other types that he can add. 
* ### Job addition 
  The developer has the ability to add another job type by:
  * creating a new microservice completely isolated and add the new discriminator of the new job type, but it should be mapped to the same job table that works under hibernate join strategy
* ### Unit tests
    Unit tests are added for all the services and implementation classes. 

## Running Swagger Ui
After starting the project, swagger UI would appear with available APIs for the overview of the system jobs in the background.
The user has the ability to add annotation and generate there service
swagger just like implemented inside Email Service or Reminder Service.

* ##Scripts and Commands
  * ### Running
     jav -jar jobManagementSystem-0.1.jar
  * ### test
     mvn clean && mvn test
  * ### failsafe plugin (integration test)
     mvn clean && mvn verify
  
* ## Property files
  * application.properties : contains H2 DB and disabled traditional logging config "in case you want to adapt it in the future " and swagger UI endpoint 
  * log4j2.xml : contains the Log4j2 configurations 
  * config.properties : contains the application multithreading properties 
  * mail.properties : contains mail properties 

* ## Event Calls
    I left this folder inside but marked it as deprecated, I implemented
away of handling jobs by calling mechanism which calls executer service which triggers any background 
jobs and execute them the next time, working in parallel and thread safe.  
But wanted to implement Event Driven Design way to follow latest best practises.  