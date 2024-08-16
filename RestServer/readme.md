# Setup 
1. Replace the values from the [application.properties](src/main/resources/application.properties) with the RabbitMQ credentials.
**Note**:Same credentials used in the worker

2. Build.
    
         build.server.bat


3. Start the server. Starts on port 8080

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
      

      
        
