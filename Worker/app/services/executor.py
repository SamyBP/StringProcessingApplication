from typing import List, Any
from app.models.task import Task
from app.models.task_factory import TaskFactory, InvalidTaskTypeException


class Response:
    id: str
    result: str
    is_success: bool


class TaskExecutor:

    def __init__(self, initial_value: str, tasks: List[Task]):
        self.initial_value = initial_value
        self.tasks = tasks

    def execute(self) -> str:
        value = self.initial_value
        for task in self.tasks:
            value = task.do(value)

        return value


class JobExecutor:
    job_data: Any

    def __init__(self, job_data: Any):
        self.job_data = job_data

    def execute(self) -> Response:
        response = Response()
        try:
            tasks = []
            for task in self.job_data["tasks"]:
                tasks.append(TaskFactory.get_task(task["type"], task["args"]))

            task_executor = TaskExecutor(self.job_data["input"], tasks)
            response.id = self.job_data["id"]
            response.result = task_executor.execute()
            response.is_success = True
        except InvalidTaskTypeException as e:
            response.id = self.job_data.get("id", None)
            response.is_success = False
            response.result = "dummy test"
        except Exception as e:
            response.id = self.job_data.get("id", None)
            response.result = str(e)
            response.is_success = False

        return response
