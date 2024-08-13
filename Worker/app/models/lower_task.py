from app.models.task import Task


class LowerTask(Task):

    def do(self, value: str) -> str:
        return value.lower()
