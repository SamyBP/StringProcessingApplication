from app.services.executor import JobExecutor, Response

from dotenv import load_dotenv
from psycopg2.pool import SimpleConnectionPool

import os
import json
import requests
import time


class Worker:
    connection_pool: SimpleConnectionPool

    def __init__(self, connection_pool: SimpleConnectionPool):
        self.connection_pool = connection_pool

    def work(self, timeout: int = 2):
        print("Waiting to get a job")
        connection = self.connection_pool.getconn()
        q_record = self.__get_queue_record(connection)
        if q_record is None:
            self.connection_pool.putconn(connection)
            time.sleep(timeout)
            return
        response = Response()
        try:
            job_data = json.loads(q_record[1])
            job_executor = JobExecutor(job_data)
            response = job_executor.execute()
            print(response.__dict__)
            r = requests.post(job_data["callback"], json=response.__dict__)
            if r.status_code != 200:
                raise Exception(f"Failed to send back the result to {job_data['callback']}: {r.text}")
        except Exception as e:
            response.result = str(e)
            response.isSuccess = False
        self.__mark_job_as_finished(connection, q_record[0], response.result, response.isSuccess)
        self.connection_pool.putconn(connection)

    @staticmethod
    def __get_queue_record(connection):
        cursor = connection.cursor()
        query = """ update mq.queue
                    set is_available = false
                    where id = (
                        select id from mq.queue where is_available = true order by id for update skip locked limit 1
                    )
                    returning id, message;
                """
        cursor.execute(query)
        job = cursor.fetchone()
        return job

    @staticmethod
    def __mark_job_as_finished(connection, id, result, is_success):
        cursor = connection.cursor()
        query = "update mq.queue set result = (%s), is_success = (%s) where id = (%s)"
        cursor.execute(query, (result, is_success, id))
        connection.commit()


load_dotenv()
db_username = os.getenv("POSTGRES_DATABASE_USER")
db_password = os.getenv("POSTGRES_DATABASE_PASSWORD")
db_host = os.getenv("POSTGRES_DATABASE_HOST")
db_port = os.getenv("POSTGRES_DATABASE_PORT")
db_name = os.getenv("POSTGRES_DATABASE")
timeout = int(os.getenv("TIMEOUT", 2))

pool = SimpleConnectionPool(minconn=1,
                            maxconn=5,
                            user=db_username,
                            password=db_password,
                            host=db_host,
                            port=db_port,
                            database=db_name)

worker = Worker(pool)

while True:
    try:
        worker.work(timeout)
    except KeyboardInterrupt:
        print("Initializing shutdown...")
        pool.closeall()
        break
    except Exception as e:
        print(f"Worker failed: {str(e)} will sleep for 10 seconds")
        time.sleep(10)
