from app.models.substring_task import SubstringTask
from app.models.lower_task import LowerTask
from app.models.upper_task import UpperTask
from app.models.trim_task import TrimTask

import unittest


class TestTask(unittest.TestCase):
    def test_whenSubstring_thenReturnsCorrectSubstring(self):
        task = SubstringTask(2, 6)

        self.assertEqual(task.do("bbbeniii"), "beni")

    def test_whenSubstringWithEndIndexOutOfBounds_raisesException(self):
        task = SubstringTask(2, 7)

        self.assertRaises(Exception, task.do, "bbbeni")

    def test_whenLower_thenReturnsCorrectLowerString(self):
        task = LowerTask()

        self.assertEqual(task.do("VALUE"), "value")

    def test_whenUpper_thenReturnsCorrectUpperString(self):
        task = UpperTask()

        self.assertEqual(task.do("value"), "VALUE")

    def test_whenTrim_thenReturnsTrimmedString(self):
        task = TrimTask("x")

        self.assertEqual(task.do("xbxexnxixxxxx"), "bxexnxi")
