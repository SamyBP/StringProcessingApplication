from app.models.task import Task
from app.models.lower_task import LowerTask
from app.models.trim_task import TrimTask
from app.models.upper_task import UpperTask
from app.models.substring_task import SubstringTask


class TaskFactory:

    @staticmethod
    def get_task(task_type: str, args) -> Task:
        match task_type:
            case "SUBSTRING":
                return SubstringTask(args["start"], args["end"])
            case "LOWER":
                return LowerTask()
            case "UPPER":
                return UpperTask()
            case "TRIM":
                return TrimTask(args["character"])
            case _:
                raise Exception("Unsupported task")
