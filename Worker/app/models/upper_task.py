from app.models.task import Task


class UpperTask(Task):

    def do(self, value: str) -> str:
        return value.upper()
