"""
:testcase_name while_module
:author Sriteja Kummita
:script_type Module
:description Demonstration of function call inside a while-loop. 'modify_list' calls 'raise_to' for each number of the
received list.
"""


def raise_to(num, power):
    return pow(num, power)


def modify_list(num_list, scalar):
    l = len(num_list)
    index = 0
    while index < l:
        num_list[index] = raise_to(num_list[index], scalar)
        index += 1
    return num_list


if __name__ == '__main__':
    modify_list([1, 3, 4], 2)
