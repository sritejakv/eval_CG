"""
:testcase_name plus_operation
:author Sriteja Kummita
:script_type Class
:description Program demonstrating dynamic typing in Python in which a variable takes different instances with in the
same program. obj is initialised with an object of AddNumber initially and then modified to the object of
ConcatenateStrings. All the classes define a method named 'add' which is invoked in the main function
"""


class AddNumbers:
    def __init__(self, param_one, param_two):
        self.param_one = param_one
        self.param_two = param_two

    def add(self):
        return self.param_one + self.param_two


class AddStrings:
    def __init__(self, param_one, param_two):
        self.param_one = param_one
        self.param_two = param_two

    def add(self):
        return self.param_one + self.param_two


class ConcatenateStrings:
    def __init__(self, param_one, param_two):
        self.param_one = param_one
        self.param_two = param_two

    def add(self):
        return str(self.param_one) + ", " + str(self.param_two)


if __name__ == '__main__':
    obj = AddNumbers(1, 2)
    print(obj.add())
    obj = ConcatenateStrings('hello', 'world')
    print(obj.add())
