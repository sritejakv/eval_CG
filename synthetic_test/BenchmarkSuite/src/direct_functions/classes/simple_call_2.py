"""
:testcase_name simple_call_2
:author Sriteja Kummita
:script_type Class
:description Program demonstrating call to a function in base class
"""


class Increment:
    def operate(self, x):
        x += 1
        return x


class Modify(Increment):
    pass


if __name__ == '__main__':
    obj = Modify()
    obj.operate(10)
