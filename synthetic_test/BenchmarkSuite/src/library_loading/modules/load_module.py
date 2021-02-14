"""
:testcase_name load_module
:author Sriteja Kummita
:script_type Module
:description Dynamically importing a module from direct_functions directory in the benchmark suite using __import__.
"""


def get_math():
    mathematics = __import__('src.direct_functions.modules.module_call_2', globals(), locals(), ["multiply"], 0)
    return mathematics


if __name__ == '__main__':
    m = get_math()
    m.multiply(3, 3)
