from abc import abstractmethod


class Task:

    @abstractmethod
    def do(self, value: str) -> str:
        pass
