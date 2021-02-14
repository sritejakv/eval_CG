"""
:testcase_name error_specification
:author Sriteja Kummita
:script_type class
:description Demonstrates the builder pattern in Python. class SpecificationBuilder uses class Error to add
true positives, false positives and false negatives accordingly.
"""


class Error:
    def __init__(self):
        self.tp = []
        self.fp = []
        self.fn = []

    def add_tp(self, true_positive):
        self.tp.append(true_positive)
        return self

    def add_fp(self, false_positive):
        self.fp.append(false_positive)
        return self

    def add_fn(self, false_negative):
        self.fn.append(false_negative)
        return self


class SpecificationBuilder:
    def __init__(self):
        self.error_spec = Error()

    def get_error_spec(self):
        return self.error_spec

    def build(self, tp, fp, fn):
        self.error_spec.add_tp(tp).add_fp(fp).add_fn(fn)
        return self


if __name__ == '__main__':
    obj = SpecificationBuilder()
    obj.build("TP", "FP", "FN")
