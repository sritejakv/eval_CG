"""
:testcase_name operations
:author Sriteja Kummita
:script_type Classes
:description Program demonstrating lambda functions in Python. This program contains four classes Add, Sub, Or, and And
for respective operation on two variables. It contains two lambda functions 'arithmetic' invoking Add and Sub classes
and 'binary' invoking Or and And classes respectively.
"""


class Add:
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def result(self):
        return self.a + self.b


class Sub:
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def result(self):
        return self.a - self.b


class Or:
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def result(self):
        return self.a or self.b


class And:
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def result(self):
        return self.a and self.b


arithmetic = lambda a, b: Add(a, b).result() + Sub(a, b).result()
binary = lambda a, b: And(a, b).result() + Or(a, b).result()


if __name__ == '__main__':
    x = arithmetic(5, 6)
    y = binary(1, 0)
    print(x, y)

