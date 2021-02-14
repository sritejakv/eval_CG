"""
:testcase_name load_class
:author Sriteja Kummita
:script_type Class
:description Dynamically importing a class from direct_functions directory in the benchmark suite using __import__.
"""


class AddClass(object):
    def get_add_class(self):
        add = __import__('src.direct_functions.classes.simple_call_1', globals(), locals(), ["Increment"], 0)
        return add


if __name__ == '__main__':
    m = AddClass().get_add_class()
    obj = m.Increment()
    obj.operate(2)
