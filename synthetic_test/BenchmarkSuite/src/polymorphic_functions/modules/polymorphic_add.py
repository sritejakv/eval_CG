"""
:testcase_name multiple_inheritance
:author Sriteja Kummita
:script_type Module
:description Program demonstrating polymorphism in Python. Function 'add' can be invoked with either two parameters or
three parameters.
"""


def check_number(a):
    assert type(a) == int


def add(x, y, z=0):
    check_number(x)
    check_number(y)
    check_number(z)
    return x + y + z


if __name__ == '__main__':
    add(1, 2)
