"""
:testcase_name nested_class
:author Sriteja Kummita
:script_type Class
:description An inner class named 'Inner' is defined in an outer class named 'Outer'. Each of the classes has a method
'to_string' defined respectively. 'to_string' method of 'Outer' calls 'to_string' method of 'Inner'.
"""


class Outer:

    def __init__(self, outer_name, inner_name):
        self.name = outer_name
        self.inner_class = Outer.Inner(inner_name)

    class Inner:
        def __init__(self, n):
            self.name = n

        def to_string(self):
            return "From inner class, name - %s" % self.name

    def to_string(self):
        return "From outer class, name - %s" % self.name + "\n" + self.inner_class.to_string()


if __name__ == '__main__':
    obj = Outer("hello", "world")
    obj.to_string()
