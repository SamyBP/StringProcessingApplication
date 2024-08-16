from app.models.task_factory import TaskFactory
from app.services.executor import TaskExecutor

from dotenv import load_dotenv

import json
import pika
import requests

import os


class Response:
    id: str
    result: str
    is_success: bool

    def __init__(self, id: str):
        self.id = id


class Worker:
    connection: pika.BlockingConnection
    queue_name: str

    def __init__(self, connection: pika.BlockingConnection, queue_name: str):
        self.connection = connection
        self.queue_name = queue_name

    def work(self):
        channel = self.connection.channel()
        channel.queue_declare(queue=self.queue_name, durable=True)
        channel.basic_qos(prefetch_count=1)
        channel.basic_consume(queue=self.queue_name, on_message_callback=self.__process_message)
        channel.start_consuming()

    @staticmethod
    def __process_message(ch, method, properties, body):
        job_data = json.loads(body.decode())
        print(f"Received job {job_data}")

        tasks = []
        for task in job_data["tasks"]:
            tasks.append(TaskFactory.get_task(task["type"], task["args"]))

        task_executor = TaskExecutor(job_data["input"], tasks)
        response = Response(job_data["id"])

        try:
            response.result = task_executor.execute()
            response.is_success = True
        except Exception as e:
            response.result = str(e)
            response.is_success = False

        print(response.__dict__)

        requests.post(job_data["callback"], json=response.__dict__)
        ch.basic_ack(delivery_tag=method.delivery_tag)


load_dotenv()
url = os.getenv("RABBITMQ_URL")
params = pika.URLParameters(url)
work_queue = os.getenv("RABBITMQ_WORK_QUEUE")
conn = pika.BlockingConnection(params)
worker = Worker(conn, work_queue)
worker.work()
