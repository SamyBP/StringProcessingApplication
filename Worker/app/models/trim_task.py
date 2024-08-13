from app.models.task import Task


class TrimTask(Task):

    def __init__(self, ch: str):
        self.ch = ch

    def do(self, value: str) -> str:
        return value.strip(self.ch)
