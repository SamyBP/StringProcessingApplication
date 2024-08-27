# Setup 
1. Configure properties from the [application.properties](src/main/resources/application.properties)

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

### 1. **POST** /api/auth/register

- **Description**: Registers a new user for the application
- **Status codes**:
   - 400: Password or Email does not match the acceptance criteria
   - 400: Email or Username already taken
   - 201: Successful registration

Accepted payload:

```json
{
   "username": "string",
   "email": "string",
   "password": "string"
}
```

### 2. **POST** /api/auth/login

- **Description**: Creates an authentication token for the user
- **Status codes**:
   - 400: Password or Email does not match acceptance criteria
   - 204: User with given email does not exist
   - 200: Successful login 

Accepted payload:

```json
{
   "email": "string",
   "password": "string"
}
```

Response body:

```json
{
   "expiresIn": "long",
   "token": "string"
}
```

### 3. **GET** /api/pipes

- **Description**: Retrieves all public pipes and the private pipes of the logged-in user
- **Headers**: "token": "string"
- **Status codes**:
   - 401: Caller does not have a valid authentication token
   - 200: Successful retrieval

Response body:

```json
[
    {
        "id": "integer",
        "user": {
            "id": "integer",
            "username": "string",
            "email": "string"
        },
        "name": "string",
        "createdAt": "yyyy-mm-ddTHH-MM-SS",
        "modules": [
            {
                "id": "integer",
                "name": "string",
                "args": {}
            }
        ],
        "public": "boolean"
    }
]
```

### 4. **POST** /api/pipes

- **Description**: Creates **the definition** of a new processing pipe. The pipe owner being the logged-in user
- **Headers**: "token": "string"
- **Status codes**:
   - 401: Caller does not have a valid authentication token
   - 400: The pipe name is blank or if it is not unique. By default, each pipe will be created with the name: **username\givenPipeName** to avoid duplication between other users pipes.
   - 400: If a request does not have at least one module attached to the pipe (null or empty list of modules) 
   - 201: Successful creation

Accepted payload:

```json
{
    "name": "string",
    "isPublic": "boolean",
    "moduleIds": [1, 2, 3, 4]
}
```

### 5 .**PUT** /api/pipes

- **Description**: Updates **the definition** of an existing processing pipe.
- **Headers**: "token": "string"
- **Status codes**:
   - 401: Caller does not have a valid authentication token
   - 401: The authenticated user is not the owner of the pipe.
   - 400: If a request does not have at least one module attached to the pipe (null or empty list of modules)
   - 201: Successful update
   - 204: The pipe does not exist
  
Accepted payload:

```json
{
    "pipeId": "integer",
    "isPublic": "boolean",
    "moduleIds": [1, 2, 3, 4]
}
```

Response body:

```json
{
   "id": "integer",
   "user": {
      "id": "integer",
      "username": "string",
      "email": "string"
   },
   "name": "string",
   "createdAt": "yyyy-mm-ddTHH-MM-SS",
   "modules": [
      {
         "id": "integer",
         "name": "string",
         "args": {}
      }
   ],
   "public": "boolean"
}
```

### 6. **GET** /api/pipes/{pipe_id}

- **Description**: Retrieves **the definition**  of the processing pipe with the pipe_id parameter.
- **Headers**: "token": "string"
- **Status codes**:
   - 401: Caller does not have a valid authentication token
   - 200: Successful retrieval

Response body:

```json
{
   "id": "integer",
   "user": {
      "id": "integer",
      "username": "string",
      "email": "string"
   },
   "name": "string",
   "createdAt": "yyyy-mm-ddTHH-MM-SS",
   "modules": [
      {
         "id": "integer",
         "name": "string",
         "args": {}
      }
   ],
   "public": "boolean"
}
```


### 7. **DELETE** /api/pipes/{pipe_id}

- **Description**: Removes the processing pipe with the pipe_id parameter.
- **Headers**: "token": "string"
- **Status codes**:
   - 401: Caller does not have a valid authentication token
   - 401: The authenticated user is not the owner of the pipe.
   - 204: Successful deletion or the pipe does not exist

### 8. **GET** /api/executions

- **Description**: Retrieves the execution history of the logged-in user.
- **Headers**: "token": "string"
- **Status codes**:
   - 401: Caller does not have a valid authentication token
   - 200: Successful retrieval

Response body:

```json
[
    {
        "createdAt": "yyyy-mm-ddTHH-MM-SS",
        "startedAt": "yyyy-mm-ddTHH-MM-SS",
        "endedAt": "yyyy-mm-ddTHH-MM-SS",
        "pipeName": "string",
        "version": "integer",
        "input": "string",
        "modules": [
            {
                "id": "integer",
                "moduleName": "string",
                "args": {}
            }
        ],
        "result": "string",
        "status": "string"
    }
]
```

### 9. **POST** /api/executions/private

- **Description**: Executes a processing pipe with the specified input string and concrete values for each of the pipe's module definition. 
Based on the specified version the job will be put in a queue for the worker to process
version = 1 => PostgreSQL, version = 2 => RabbitMq 

- Headers: "token": "string"
- Status codes:
   - 401: Caller does not have a valid authentication token
   - 401: The logged-in user does not have execution permissions for the pipe (the pipe is private and he is not the owner)
   - 202: The job was put to queue

Accepted payload:

```json
{
    "input": "string",
    "pipeId": "integer",
    "version": "integer",
    "modules": [
        {
            "name": "string",
            "args": {}
        }
    ]
}
```

### 10. **POST** /api/executions/public
- **Description**: Same as /api/execution/private. Does not require authentication. The result must be handled by the caller

Accepted payload:

```json
{
    "id": "integer",
    "input": "string",
    "callback": "URL to handle the result from the worker",
    "modules": [
        {
            "type": "string",
            "args": {}
        }
    ]
}
```

### 11. **POST** /api/executions/callback

- **Description**: Default callback URL sent to the worker when an execution request is made at /api/executions/private.
                  The execution response of the pipe is persisted

Accepted payload:

```json
{
    "id": "integer",
    "result": "string",
    "isSuccess": "boolean",
    "startedAt": "timestamp",
    "endedAt": "timestamp"
}
```
