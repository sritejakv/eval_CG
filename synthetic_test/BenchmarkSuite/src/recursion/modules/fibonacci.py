"""
:testcase_name static_call_1
:author Sriteja Kummita
:script_type Module
:description Function fibonacci calculates the sum of fibonacci series until the given number recursively
"""


def fibonacci(n):
    if n == 1:
        return 0
    elif n == 2:
        return 1
    else:
        return fibonacci(n - 1) + fibonacci(n - 2)


if __name__ == '__main__':
    fibonacci(9)
