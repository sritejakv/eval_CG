"""
:testcase_name simple_call_1
:author Sriteja Kummita
:script_type Class
:description Program demonstrating call to a function using in the class Increment invoked using its object
"""


class Increment:
    def operate(self, x):
        x += 1
        return x


if __name__ == '__main__':
    obj = Increment()
    obj.operate(10)
