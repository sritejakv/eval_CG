"""
:testcase_name nested_factorial
:author Sriteja Kummita
:script_type Module
:description Demonstrates the use of inner functions with the example of calculating factorial of a number. 'factorial'
contains an inner recursive method 'inner_factorial' to calculate the factorial of the number and is called from
outer method.
"""


def factorial(num):
    if not isinstance(num, int):
        raise TypeError("Number is not of type - Integer")

    def inner_factorial(n):
        if n <= 1:
            return 1
        return n * inner_factorial(n - 1)
    return inner_factorial(num)


if __name__ == '__main__':
    factorial(5)
