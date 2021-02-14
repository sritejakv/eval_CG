"""
:testcase_name setattr_fun
:author Sriteja Kummita
:script_type Class
:description Dynamically setting the function to be called using the object of SetattrFun class
"""


class SetattrFun:
    def __init__(self):
        self.function_to_call = None

    def function_one(self):
        return 'hello'

    def function_two(self):
        return 'world'

    def set_function_to_call(self, fn_name):
        if fn_name == 'function_one':
            setattr(self, 'function_to_call', self.function_one)
        elif fn_name == 'function_two':
            setattr(self, 'function_to_call', self.function_two)


if __name__ == '__main__':
    obj = SetattrFun()
    obj.set_function_to_call('function_one')
    obj.function_to_call()
    # print(obj.function_to_call())

