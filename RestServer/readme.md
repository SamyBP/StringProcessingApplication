# Setup 
1. Replace the values from the [application.properties](src/main/resources/application.properties) with the RabbitMQ credentials.
**Note**:Same credentials used in the worker

2. Build.
    
         build.server.bat


3. Start the server. Starts on port 8080

         start.server.bat

# Endpoints

   | GET | POST | PUT | DELETE |          Endpoint         |
   |:---:|:----:|:---:|:------:|:-------------------------:|
   |     |  X   |     |        |     /api/auth/register    |
   |     |  X   |     |        |      /api/auth/login      |
   |  X  |  X   |  X  |        |         /api/pipes        |
   |  X  |      |     |    X   |    /api/pipes/{pipe_id}   |
   |  X  |      |     |        |      /api/executions      |
   |     |  X   |     |        |  /api/executions/private  |
   |     |  X   |     |        |   /api/executions/public  |
   |     |  X   |     |        |  /api/executions/callback |


1. **POST** /api/executions/public

Description: Puts the processingPipe in the queue(version=1 -> Rabbitmq, version=2 -> Postgres) for the worker to process

accepted payload:

      {
         "id": "integer",
         "input": "string",
         "callback": "string",
         "version": "integer"
         "modules": [
            {
               "type": "string",
               "args": {}
            }
         ]
      }


2. **POST** /api/executions/private 
* **Headers**: token: <jwt_token>

Description: Based on the version, puts the processingPipe in the correct queue for the worker to process and saves the execution
for the authenticated user

accepted payload:

      {
         "input": "string",
         "pipe_id": "integer",
         "version": "integer",
         "modules": [
            {
               "id": "integer",
               "args": {}
            }
         ]
      }

3. **GET** /api/executions
* **Headers**: token: <jwt_token>

Description: Retrieves the execution history of the authenticated user

example response:

      {
         "executions": [
            {
               "created_at": "timestamp",
               "started_at": "timestamp",
               "ended_at": "timestamp",
               "pipe_name": "string",
               "input": "string",
               "modules": [
                  {
                     "type": "string",
                     "args": {}
                  }
               ],
               "result": "string",
               "status": "string"
            }
         ]
      }

4. **GET** /api/executions/{pipe_id}
* **Headers**: token: <jwt_token>

Description: retrieve the execution of a specified pipe for the authenticated user
example response:

      {
         "created_at": "timestamp",
         "started_at": "timestamp",
         "ended_at": "timestamp",
         "pipe_name": "string",
         "input": "string",
         "modules": [
            {
               "type": "string",
               "args": {}
            }
         ],
         "result": "string",
         "status": "string"
      }

5. **POST** /api/pipes
* **HEADERS** token: <jwt_token>

Description: create a new processing pipe for the authenticated user
accepted payload:


      {
        "name": "string",
        "is_public": "boolean",
        "moduleTypeIds": [1, 2]
      }


6. **GET** /api/pipes
* **HEADERS** token: <jwt_token>

Description: retrieves all pipes created by the authenticated user and public ones

example response:
   
   
       [
         {
            "id": ,
            "name": ,
            "createdAt": ,
            "modules": [
               {
                  "id": ,
                  "name": ,
                  "args": {
                     "start": ,
                     "end": 
                  }
               }
            ],
            "public": 
         }
       ] 



7. **DELETE** /api/pipes/{pipeId}

      
        
