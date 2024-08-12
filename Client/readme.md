

# Instalation step

1. create a virtual env


    virtualenv venv


2. activate virtualenv
    

    ./venv/scripts/activate


3. install all requirements

    
    pip install -r requirements.txt


# Run 

1. create a .env in the current directory. Replace the value with the actual endpoint of the rest server application


    STRING_PROCESSING_ENDPOINT_APP="http://localhost:8002/test_handle_execution"


2. start the server 
    


    run.server.bat

3. swagger can be accessed on localhost:8002/docs 


# Endpoints 

* /callback type: post

accepted payload:

    {
      "id": "string",
      "result": "string",
      "is_success": true
    }

Description: It prints to stdout the received payload

* /trigger_execution type: post

accepted payload:

    {
      "id": "string",
      "callback": "string",
      "tasks": [
        {
          "type": "string",
          "args": {}
        }
      ]
    }

description: it makes a post request to STRING_PROCESSING_ENDPOINT_APP endpoint with the given payload. It prints to stdout the response status code and the response body.


* /test_handle_execution type: post

Accepted payload:

    {
      "id": "string",
      "callback": "string",
      "tasks": [
        {
          "type": "string",
          "args": {}
        }
      ]
    }

Description: it prints to stdout the received payload. It's a mocking endpoint of the one implemented on RestServer app.

