"""
:testcase_name multilevel_inheritance
:author Sriteja Kummita
:script_type Class
:description Program demonstrating multilevel inheritance in Python. Shape is the superclass for all its subclasses in
which 'area' method is defined. It is invoked using a subclass object
"""


class Shape:
    def __init__(self, l, b):
        self.length = l
        self.breadth = b

    def area(self):
        return self.length * self.breadth


class Square(Shape):
    def __init__(self, s):
        super().__init__(s, s)


class Cube(Square):
    def __init__(self, s):
        super().__init__(s)

    def volume(self):
        return self.length * self.length * self.length


if __name__ == '__main__':
    c = Cube(5)
    print(c.area())
