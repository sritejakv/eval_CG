"""
:testcase_name change_foo_to_bar
:author Sriteja Kummita
:script_type Module
:description Implementation using decorators in Python. Decorator 'modify' changes the parameter of the function call
from 'foo' to 'bar' and viceversa. Method 'call' invokes the received parameter.
"""


def foo():
    return 'Foo'


def bar():
    return 'Bar'


def modify(func):
    def wrapper(*args):
        if args[0]() == 'Foo':
            return func(bar)
        else:
            return func(args[0])
    return wrapper


@modify
def call(obj):
    return obj()


if __name__ == '__main__':
    call(foo)
