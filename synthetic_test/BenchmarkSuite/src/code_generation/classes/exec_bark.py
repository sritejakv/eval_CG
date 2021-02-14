"""
:testcase_name exec_bark
:author Sriteja Kummita
:script_type Class
:description 'exec' function in Python can be used to execute a block of Python code.
"""

x = """
class Dog:
    def bark(self):
        print("Bow bow!")


class Cat:
    def bark(self):
        print("Meow meow!")


class Animal:
    def __init__(self, animal):
        self.a = animal

    def howl(self):
        self.a.bark()

Animal(Dog()).howl()
"""

if __name__ == '__main__':
    exec(x)
    # Outputs: Bow bow!

