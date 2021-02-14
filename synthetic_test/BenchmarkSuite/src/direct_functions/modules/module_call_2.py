"""
:testcase_name module_call_2
:author Sriteja Kummita
:script_type Module
:description This program contains two functions 'add' and 'multiply'. There is a call to function 'add' from
the function 'multiply'.
"""


def add(x, y):
    return x + y


def multiply(x, y):
    z = 0
    while x > 0:
        z = add(z, y)
        x -= 1
    return z


if __name__ == '__main__':
    multiply(4, 4)
