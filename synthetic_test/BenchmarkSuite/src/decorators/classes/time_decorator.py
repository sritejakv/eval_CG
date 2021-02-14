"""
:testcase_name time_decorator
:author Sriteja Kummita
:script_type Class
:description Implementation using decorators in Python. Class 'Timer' records the time of the function being executed
and can be used as a decorator. Method 'factorial' is annotated with the decorator 'Timer' as shown. For every invoke of
the method 'factorial' code in '__call__' method of Timer is executed
"""


from time import time


class Timer:
    def __init__(self, func):
        self.function = func

    def __call__(self, *args, **kwargs):
        start_time = time()
        res = self.function(*args, **kwargs)
        end_time = time()
        print('Execution took - {} seconds.'.format(end_time - start_time))
        return res


@Timer
def factorial(n):
    res = 0
    while n >= 1:
        res = res * n
        n -= 1
    return res


if __name__ == '__main__':
    factorial(5)
