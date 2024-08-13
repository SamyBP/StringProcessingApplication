import json
import pika
import requests

from app.models.task_factory import TaskFactory
from app.services.executor import TaskExecutor

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost', virtual_host="vh1")
)

channel = connection.channel()
channel.queue_declare(queue='work_queue', durable=True)
print("Waiting to receive a job")


def callback(ch, method, properties, body):
    job_data = json.loads(body.decode())
    print(f"Received job {job_data}")

    tasks = []
    for task in job_data["tasks"]:
        tasks.append(TaskFactory.get_task(task["type"], task["args"]))

    task_executor = TaskExecutor(job_data["input"], tasks)

    try:
        success_response = {
            "id": job_data["id"],
            "processedInput": task_executor.execute()
        }
        requests.post(job_data["callback"], json=success_response)
    except Exception as e:
        failure_response = {
            "id": job_data["id"],
            "errorMessage": repr(e)
        }
        requests.put(job_data["callback"], json=failure_response)

    ch.basic_ack(delivery_tag=method.delivery_tag)


channel.basic_qos(prefetch_count=1)
channel.basic_consume(queue='work_queue', on_message_callback=callback)

channel.start_consuming()
