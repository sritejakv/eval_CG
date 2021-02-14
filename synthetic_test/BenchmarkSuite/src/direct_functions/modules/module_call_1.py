"""
:testcase_name module_call_1
:author Sriteja Kummita
:script_type Module
:description This program contains two functions 'print_chars_in_upper_case' that implements a generator to
print characters in the argument and 'get_upper_case' which returns the upper case of the passed character.
'print_chars' prints the upper case characters by executing the generator.
"""


def get_upper_case(x):
    return x.upper()


def print_chars_in_upper_case(x):
    length = len(x)
    for i in range(length):
        yield get_upper_case(x[i])


def print_chars(gen, length):
    for i in range(length):
        try:
            print(next(gen))
        except StopIteration:
            print("Max limit reached.")


if __name__ == '__main__':
    x = "string"
    j = print_chars_in_upper_case(x)
    print_chars(j, len(x))
