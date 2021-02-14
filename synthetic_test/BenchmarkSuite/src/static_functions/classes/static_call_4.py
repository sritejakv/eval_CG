"""
:testcase_name static_call_4
:author Sriteja Kummita
:script_type class
:description This file contains a static method in the class 'One'. This style of
declaring a static function is followed in Python 2 rather than in Python 3.
"""


class One:
    def static_function():
        return "hello"

    static_function = staticmethod(static_function)


if __name__ == '__main__':
    One.static_function()
