"""
:testcase_name exceptions
:author Sriteja Kummita
:script_type Class
:description This program features exception handling in python.
"""


class CustomError(Exception):
    pass


class ExceptionHandling(object):
    def exception_caught(self, e):
        print("Exception caught: " + str(e))

    def finally_block(self):
        print("Executing finally block")

    def handle(self, x):
        try:
            if x != 1:
                raise CustomError("Number not equal to one")
        except CustomError as e:
            self.exception_caught(e)
        finally:
            self.finally_block()


if __name__ == '__main__':
    obj = ExceptionHandling()
    obj.handle(2)
