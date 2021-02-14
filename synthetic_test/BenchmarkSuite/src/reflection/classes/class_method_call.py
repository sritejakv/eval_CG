"""
:testcase_name class_method_call
:author Sriteja Kummita
:script_type class
:description This file contains two class methods in the class ClassMethodFactory and are called based on the parameter passed to the constructor in the main_method.
"""


class ClassMethodFactory:
    def __init__(self, param):
        self.param = param
        self.method_choices = {'m1': self.method_one, 'm2': self.method_two}

    @classmethod
    def method_one(cls):
        print("Method 1")

    @classmethod
    def method_two(cls):
        print("Method 2")

    def main_method(self):
        self.method_choices[self.param]()


if __name__ == '__main__':
    obj = ClassMethodFactory('m2')
    obj.main_method()
