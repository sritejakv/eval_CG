"""
:testcase_name static_call_3
:author Sriteja Kummita
:script_type class
:description This file contains two static methods in the class StaticMethodFactory and are called based
    on the parameter passed to the constructor in the main_method.
"""


class StaticMethodFactory:
    def __init__(self, param):
        self.param = param
        self.method_choices = {'m1': StaticMethodFactory.method_one, 'm2': StaticMethodFactory.method_two}

    @staticmethod
    def method_one():
        print("Method 1")

    @staticmethod
    def method_two():
        print("Method 2")

    def main_method(self):
        self.method_choices[self.param]()


if __name__ == '__main__':
    obj = StaticMethodFactory('m1')
    obj.main_method()
