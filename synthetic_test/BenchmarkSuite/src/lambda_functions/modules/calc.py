"""
:testcase_name calc
:author Sriteja Kummita
:script_type Module
:description Program demonstrating lambda functions in Python. It contains three lambda functions add, sub, and
square performing the respective mathematical operations
"""


add = lambda a, b: a + b
sub = lambda a, b: a - b
square = lambda a: a * a


if __name__ == '__main__':
    x = add(5, 6)
    y = sub(5, 6)
    z = square(5)
    print(x, y, z)

