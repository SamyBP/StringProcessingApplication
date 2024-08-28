## Setup

0. Optional: drop database if exists

        psql -U postgres -c "drop database message_queue_db;"

1. Create the database user.Replace password in the script

   ```postgresql
    create user mq_admin with
    createdb
    encrypted password '';
   ```

2. Create the database.

        psql -U mq_admin -d postgres -c "create database message_queue_db;"

3. Restore the database schema

        psql -U mq_admin message_queue_db < PostgreSQL\message.queue.dump.sql


## Description

- Owner: mq_admin
- Schemas: mq 
- The database contains one table mq.queue(id, message, result, is_available, is_success, created_at).
- Partial index: is_available = true