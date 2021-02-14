"""
:testcase_name greet_decorator
:author Sriteja Kummita
:script_type Class
:description Implementation using decorators in Python. For every invoke of the function 'print_name' in class Person,
code in the decorator function 'greeting' is executed
"""


def greeting(func):
    print("Good morning, ")
    return func


class Person:
    def __init__(self, name):
        self.name = name

    @greeting
    def print_name(self):
        print(self.name + "!")


if __name__ == '__main__':
    obj = Person("Moana")
    obj.print_name()
