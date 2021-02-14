"""
:testcase_name quack
:author Sriteja Kummita
:script_type Class
:description Program demonstrating duck typing in Python. This program contains three classes Duck, Mallardm, Person,
and a function shoot. Duck typing is demonstrated in the function 'shoot'. It assumes that the parameter it receives will
always contain a method 'quack' associated to it and invokes it.
"""

class Duck:
    def quack(self):
        print("Quack!")


class Mallard:
    def quack(self):
        print("Quack quack!")


class Person:
    def quack(self):
        print("Help!")


def shoot(bird):
    bird.quack()


if __name__ == '__main__':
    for b in [Duck(), Mallard(), Person()]:
        shoot(b)
