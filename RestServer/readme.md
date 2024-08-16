# Setup 

1. Build. Requires jdk-21
    
         mvnw package


2. Start the server. Starts on port 8080

         start.server.bat

# Endpoints

* /jobs type: post

accepted payload:
```json
{
      "id": "string",
      "input": "string",
      "callback": "string",
      "tasks": [
         {
            "type": "string",
            "args": {}
         }
      ]
}
```
    

Description: Puts the job in the queue for the worker to process
      

      
        
