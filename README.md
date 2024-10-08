# String Processing Application

## Description

The application is designed to handle "heavy" processing on an input string in a pipeline manner, having the following flow:
- The client initiates a request directed to the load balancer who distributes it to the next available server
- The server then passes the work to one of the workers in a queue like manner (either in a RabbitMQ WorkQueue or in a PostgreSQL database) 
and immediately let's back the client know that the execution was triggered.
- The client is then notified via a callback made by the worker after it has finished the processing

User stories:
- A user can login via email and password(minimum 8 digits, at least one lowercase, at least one uppercase, at least one digit)
- A user can perform CRUD operations on various pipes.
  - A user can create a new processing pipe, choosing the definition modules and the pipe's visibility (public or private).
  - A user can update or delete only those pipes that belong to him
  - A user can see the definition of the pipes created by him and all public pipes.
- A user can execute only his or other user's public pipes
  - At each execution a user can select different inputs and properties for each of the pipe's defined modules.
- A user can view his execution history.
  - For each execution, the user can see 
    - The time when he triggered the execution
    - The time when one of the worker started the processing
    - The time when the worker finished the processing
    - The chosen version/"strategy" for server-worker messaging (1 -> Publish/Subscribe (RabbitMQ), 2 -> Pooling (continuously reading from the database))
    - The string input
    - The processing status (NOT_PROCESSED, ASSIGNED, FINISHED, ERROR)
    - The result/error message
   

## Requirements

1. Docker, you can install it from here https://docs.docker.com/engine/install/
2. Node.js, https://nodejs.org/en/download/package-manager


## Run app

1. First build the rest server application.Check [RestServer](./RestServer/readme.md) for build guide **(Setup:1. and 2.)**
2. Start the backend, from projects root run:

         start.app.bat --build 

- **Note**: --build options is necessary on first run to create the docker images.
- **Note**: the scripts will dockerize the backend application providing some default credentials. Change them in the **environment** from each service from [docker-compose](./docker-compose.yml).  

3. To start the frontend check [fronted.guide](/frontend/README.md) 


## Scaling

1. Replace the values in the [start.app.bat](./start.app.bat):
      
         SERVER_REPLICAS_COUNT=<int number_of_servers>
         WORKER_V1_REPLICAS_COUNT=<int number_of_workers>
         WORKER_V2_REPLICAS_COUNT=<int number_of_workers>

2. Run:

         start.app.bat

- **Note** a configuration file for Nginx load balancer will be created(replaced) in the [LoadBalancer](./LoadBalancer) directory

    