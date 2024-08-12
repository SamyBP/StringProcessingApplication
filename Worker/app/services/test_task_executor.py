import unittest

from app.models.tasks import SubstringTask, TrimTask, LowerTask
from app.services.executor import TaskExecutor


class TestTaskExecutor(unittest.TestCase):

    def test_whenTasksCanBeDone_returnsCorrectResult(self):
        given_tasks = [TrimTask(" "), LowerTask(), SubstringTask(0, 4)]
        executor = TaskExecutor("   Beni amin    ", given_tasks)

        self.assertEqual(executor.execute(), "beni")
