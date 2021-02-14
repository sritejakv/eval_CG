"""
:testcase_name if_module
:author Sriteja Kummita
:script_type Module
:description Module containing multiple if-statements containing function calls to 'get_increment' and 'get_decrement'.
'modify_alternative' returns a modified list after incrementing multiples of 2 and decrementing multiples of 3.
"""


def get_decrement(n):
    n -= 1
    return n


def get_increment(n):
    n += 1
    return n


def modify_alternative(num_list):
    index = 0
    for i in num_list:
        if i % 2 == 0:
            num_list[index] = get_increment(i)
        elif i % 3 == 0:
            num_list[index] = get_decrement(i)
        else:
            num_list[index] = i
        index += 1
    return num_list


if __name__ == '__main__':
    modify_alternative([1, 2, 3, 4, 5, 6, 7])
