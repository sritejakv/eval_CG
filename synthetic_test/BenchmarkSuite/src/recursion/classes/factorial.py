"""
:testcase_name factorial
:author Sriteja Kummita
:script_type Class
:description Class, RecursiveFactorial contains a function that calculates factorial of a given number recursively
"""


class RecursiveFactorial:
    def factorial(self, n):
        if n <= 1:
            return 1
        return n * self.factorial(n - 1)


if __name__ == '__main__':
    obj = RecursiveFactorial()
    obj.factorial(5)
