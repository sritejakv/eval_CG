"""
:testcase_name visitor
:author Sriteja Kummita
:script_type class
:description Demonstrates the visitor pattern using reflection similar to Python AST module implementation.
"""


class Root:
    pass


class LevelOne(Root):
    pass


class LevelTwo(Root):
    pass


class LevelThree(Root):
    pass


class Visitor:
    def visit(self, node, *args, **kwargs):
        method_name = None
        for cls in node.__class__.__mro__:
            method_name = getattr(self, 'visit_' + str(cls.__name__), None)
            if method_name:
                break
            else:
                continue

        if not method_name:
            method_name = self.generic_visit
        return method_name(node, *args, **kwargs)

    def generic_visit(self, *args, **kwargs):
        print("Generic visit")

    def visit_LevelOne(self, *args, **kwargs):
        print("visit LevelOne")

    def visit_LevelThree(self, *args, **kwargs):
        print("visit LevelThree")


if __name__ == '__main__':
    one, two, three = LevelOne(), LevelTwo(), LevelThree()
    v = Visitor()
    v.visit(one)
    v.visit(two)
    v.visit(three)
