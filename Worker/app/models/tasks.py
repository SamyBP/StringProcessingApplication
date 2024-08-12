from abc import abstractmethod


class Task:

    @abstractmethod
    def do(self, value: str) -> str:
        pass


class SubstringTask(Task):

    def __init__(self, start: int, end: int):
        self.start = start
        self.end = end

    def do(self, value: str) -> str:
        if self.end > len(value):
            raise Exception("end index out of bounds")
        return value[self.start:self.end]


class LowerTask(Task):

    def do(self, value: str) -> str:
        return value.lower()


class UpperTask(Task):

    def do(self, value: str) -> str:
        return value.upper()


class TrimTask(Task):

    def __init__(self, ch: str):
        self.ch = ch

    def do(self, value: str) -> str:
        return value.strip(self.ch)
