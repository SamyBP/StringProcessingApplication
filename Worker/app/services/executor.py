from typing import List
from app.models.task import Task


class TaskExecutor:

    def __init__(self, initial_value: str, tasks: List[Task]):
        self.initial_value = initial_value
        self.tasks = tasks

    def execute(self) -> str:
        value = self.initial_value
        for task in self.tasks:
            value = task.do(value)

        return value
