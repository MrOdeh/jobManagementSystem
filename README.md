# Job Management System
This project was created to handle different types of jobs. 

## Implementation & Assumptions
* ### Priorities Handling
   I assumed that we have three priorities, High, Medium, Low.
If a jobs is executed in the background has same execution time, then job with higher priority is executed.
* ### Scheduling
   A background jobs is running to handle the executions of 
each Job management jobs. Upon job creation the default state 
is queued, if the execution time is immediately, 
then the job state changes to running and is executed by the 
Job in the background. After creation based on the  output 
of the executed job either it changes the state to Successful or Failed.  
* ### Reliability 
  Each job is handled that it never effect the system is it fails, only the 
state is set to failed. HOW HOW HOW 
* ### Execution
    At the start of the project, I create 300 Jobs with different 
types, and execution times, just to simulate the project work. These jobs output can be viewed in the logs. 
And Job APIs can be used to fetch them. The user
still has the ability to create another jobs of type Reminder or 
Email and in the future other types that he can add. 
* ### Job addition 
  the system admin has the ability to add another job type by:
  * ii create a new microservice completely isolated and add the new discriminator a new job type, but it should be mapped to the same job table that works under hibernate joined strategy
* ### Unit tests
    Unit tests are added for all the services and implementation classes. 

## Running Swagger Ui
After starting the project, swagger UI would appear with available APIs for the overview of the system jobs in the background.
The user has the ability to add annotation and generate there service
swagger just like implemented inside Email Service or Reminder Service.


