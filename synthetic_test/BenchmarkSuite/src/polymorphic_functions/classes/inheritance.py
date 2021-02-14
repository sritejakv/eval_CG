"""
:testcase_name inheritance
:author Sriteja Kummita
:script_type Class
:description Program demonstrating inheritance in Python. Class Square extends class Shape in which 'area' method is
defined. It is invoked using a subclass object
"""


class NumCheck:
    def __init__(self, x):
        self.x = x

    def __enter__(self):
        assert type(self.x) == int

    def __exit__(self, exc_type, exc_val, exc_tb):
        pass


class Shape:
    def __init__(self, l, b):
        self.length = l
        self.breadth = b

    def area(self):
        with NumCheck(self.length) and NumCheck(self.breadth):
            return self.length * self.breadth


class Square(Shape):
    def __init__(self, s):
        super().__init__(s, s)


if __name__ == '__main__':
    obj = Square(5)
    obj.area()
