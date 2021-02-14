"""
:testcase_name eval_bark
:author Sriteja Kummita
:script_type Class
:description 'eval' function in Python can be used to evaluate an expression in Python code.
This implements invokes the method 'bark' in class 'Dog' using 'eval'
"""


class Dog:
    def bark(self):
        print("Bow bow!")


class Cat:
    def bark(self):
        print("Meow meow!")


if __name__ == '__main__':
    eval("Dog().bark()")
