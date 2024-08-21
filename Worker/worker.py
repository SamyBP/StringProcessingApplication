from app.services.executor import JobExecutor

from dotenv import load_dotenv

import json
import pika
import requests

import os


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
        job_executor = JobExecutor(job_data)
        response = job_executor.execute()
        requests.post(job_data["callback"], json=response.__dict__)
        ch.basic_ack(delivery_tag=method.delivery_tag)


load_dotenv()
url = os.getenv("RABBITMQ_URL")
params = pika.URLParameters(url)
work_queue = os.getenv("RABBITMQ_WORK_QUEUE")
conn = pika.BlockingConnection(params)
worker = Worker(conn, work_queue)
worker.work()
