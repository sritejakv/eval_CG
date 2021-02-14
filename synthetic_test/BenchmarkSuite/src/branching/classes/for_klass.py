"""
:testcase_name for_klass
:author Sriteja Kummita
:script_type Class
:description Demonstration of function call inside a for-loop. 'increment_even_numbers' contains a for-loop in which
only even numbers in the list are incremented by calling the method 'get_increment'.
"""


class IncEven(object):
    def __init__(self, l):
        self.numbers = l

    def get_increment(self, num):
        num += 1
        return num

    def increment_even_numbers(self):
        index = 0
        for num in self.numbers:
            if num % 2 == 0:
                temp = self.get_increment(num)
                del self.numbers[index]
                self.numbers.insert(index, temp)
            index += 1
        return self.numbers


if __name__ == '__main__':
    obj = IncEven([1, 2, 3, 4])
    obj.increment_even_numbers()
