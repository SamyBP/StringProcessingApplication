# Setup
1. create a virtual env
      
         virtualenv venv   

2. activate virtualenv
         
         ./venv/scripts/activate

3. install requirements

         pip install -r requirements.txt
    
4. create .env and replace the values

         RABBITMQ_URL=amqp://username:password@host:5672/virutal_host
         RABBITMQ_WORK_QUEUE=queue_name
         POSTGRES_DATABASE_USER=
         POSTGRES_DATABASE_PASSWORD=
         POSTGRES_DATABASE_HOST=
         POSTGRES_DATABASE_PORT=
         POSTGRES_DATABASE=
         TIMEOUT=
           

# Run

        run.worker.bat <version>

- Where **version** can be either 1 -> worker will take jobs from RabbitMQ, 2 -> worker will take jobs from database

# Description

- Takes a job from the queue, processes the tasks and sends back the result to the client through a post made at the callback url