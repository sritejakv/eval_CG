"""
:testcase_name static_call_1
:author Sriteja Kummita
:script_type Class
:description A static method in a class is called using the its class name
"""


class Foo:
    @staticmethod
    def test_method():
        return 'Foo'


if __name__ == '__main__':
    Foo.test_method()
