from app.models.task import Task


class SubstringTask(Task):

    def __init__(self, start: int, end: int):
        self.start = start
        self.end = end

    def do(self, value: str) -> str:
        if self.end > len(value):
            raise Exception("end index out of bounds")
        return value[self.start:self.end]
