"""
:testcase_name greet
:author Sriteja Kummita
:script_type Class
:description __getattr__ is called when a property/method of a class is used. This class allows three functions which
are not defined in the class but can be called using reflection
"""


class Greet:
    def __init__(self, name):
        self.name = name

    def __getattr__(self, function_name):
        allowed = ['good_morning', 'good_afternoon', 'good_night']

        def call_():
            greeting = function_name.replace('_', ' ')
            if function_name in allowed:
                return f"{greeting.capitalize()}, {self.name}!"
            else:
                raise ValueError(f"Invalid name or greeting. name: {self.name}, greeting: {greeting}")
        return call_


if __name__ == '__main__':
    greet = Greet("Nemo")
    print(greet.good_morning())
    # Outputs: Good morning, Nemo!
    print(greet.good_afternoon())
    # Outputs: Good afternoon, Nemo!
    print(greet.good_night())
    # Outputs: Good night, Nemo!

