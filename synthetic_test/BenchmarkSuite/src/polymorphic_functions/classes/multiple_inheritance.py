"""
:testcase_name multiple_inheritance
:author Sriteja Kummita
:script_type Class
:description Program demonstrating multiple inheritance in Python. Class Cuboid extends Shape and HexColor each defining
methods which are called using the object of Cuboid
"""


class Shape:
    def __init__(self, l, b):
        self.length = l
        self.breadth = b

    def area(self):
        return self.length * self.breadth


class HexColor:
    def __init__(self, r, g, bl):
        self.red = r
        self.green = g
        self.blue = bl

    def get_color(self):
        return [self.red, self.green, self.blue]


class Cuboid(Shape, HexColor):
    def __init__(self, l, b, h, r, g, bl):
        Shape.__init__(self, l, b)
        HexColor.__init__(self, r, g, bl)
        self.height = h

    def volume(self):
        return self.length * self.breadth * self.height


if __name__ == '__main__':
    cu = Cuboid(4, 5, 6, 100, 80, 90)
    cu.area()
