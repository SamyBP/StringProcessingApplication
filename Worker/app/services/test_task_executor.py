import unittest

from app.models.substring_task import SubstringTask
from app.models.trim_task import TrimTask
from app.models.lower_task import LowerTask
from app.services.executor import TaskExecutor


class TestTaskExecutor(unittest.TestCase):

    def test_whenTasksCanBeDone_returnsCorrectResult(self):
        given_tasks = [TrimTask(" "), LowerTask(), SubstringTask(0, 4)]
        executor = TaskExecutor("   Beni amin    ", given_tasks)

        self.assertEqual(executor.execute(), "beni")
