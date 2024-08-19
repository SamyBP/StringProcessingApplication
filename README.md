# String Processing Application


## Run app

1. First build the rest server application.Check [RestServer](./RestServer/readme.md) for build guide
2. Run:

         start.app.bat --build 

- **Note**: --build options is necessary on first run

## Scaling

1. Replace the values in the [start.app.bat](./start.app.bat):
      
         SERVER_REPLICAS_COUNT=<number of servers>
         WORKER_REPLICAS_COUNT=<number of workers>
2. Run:

         start.app.bat

- **Note** a configuration file for Nginx will be created(replaced) in the [Nginx](./Nginx) directory used by the load balancer

    