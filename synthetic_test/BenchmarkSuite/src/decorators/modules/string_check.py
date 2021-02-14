"""
:testcase_name string_check
:author Sriteja Kummita
:script_type Module
:description Implementation using decorators in Python. Decorator 'string_check' checks for any strings in the first
argument of the function being invoked and throws error respectively. Method 'add_list' returns the sum of all elements
of the list and is annotated with the decorator 'string_check' to check for strings in the parameter being passed
"""


def string_check(func):
    def wrapper(*args):
        params = args[0]
        if any([isinstance(i, str) for i in params]):
            raise TypeError("All items in the list are not numbers.")
        else:
            return func(args[0])
    return wrapper


@string_check
def add_list(numbers):
    return sum(numbers)


if __name__ == '__main__':
    add_list([1, 2, '3'])
