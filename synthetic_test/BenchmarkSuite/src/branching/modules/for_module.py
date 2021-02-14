"""
:testcase_name for_module
:author Sriteja Kummita
:script_type Module
:description Demonstration of function call inside a for-loop. 'increment_even_numbers' contains a for-loop in which
only even numbers in the list are incremented by calling the method 'get_increment'.
"""


def get_increment(n):
    n += 1
    return n


def increment_even_numbers(l):
    index = 0
    for num in l:
        if num % 2 == 0:
            l[index] = get_increment(num)
        index += 1
    return l


if __name__ == '__main__':
    increment_even_numbers([1, 2, 3, 4])
