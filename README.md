# String Processing Application


## Run app

1. First build the rest server application.Check [RestServer](./RestServer/readme.md) for build guide **(Setup:1. and 2.)**
2. Run:

         start.app.bat --build 

- **Note**: --build options is necessary on first run to create the docker images

## Scaling

1. Replace the values in the [start.app.bat](./start.app.bat):
      
         SERVER_REPLICAS_COUNT=<int number_of_servers>
         WORKER_REPLICAS_COUNT=<int number_of_workers>
2. Run:

         start.app.bat

- **Note** a configuration file for Nginx load balancer will be created(replaced) in the [Nginx](./Nginx).

    