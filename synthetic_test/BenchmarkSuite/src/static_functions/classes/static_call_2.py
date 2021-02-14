"""
:testcase_name static_call_2
:author Sriteja Kummita
:script_type class
:description A static method in the superclass is called using the object of its subclass
"""


class Foo:
    @staticmethod
    def test_method():
        return 'Foo'


class Bar(Foo):
    def class_method(self, x):
        x += 1
        return x


if __name__ == '__main__':
    Bar.test_method()
