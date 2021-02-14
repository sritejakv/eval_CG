"""
:testcase_name eval_concat
:author Sriteja Kummita
:script_type Module
:description 'eval' function in Python can be used to execute a block of Python code. Methods 'concat_str' and
'concat_dict' are invoked using 'eval' in this program
"""


def concat_str(a, b):
    return a + b


def concat_dict(a, b):
    a.update(b.items())
    return a


if __name__ == '__main__':
    eval('print(concat_str(\"hello \", \"world!\"))')
    eval('print(concat_dict({1: 2}, {3: 4}))')

