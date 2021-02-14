"""
:testcase_name record_metrics
:author Sriteja Kummita
:script_type class
:description Metrics contains the getters and setter for precision and recall. RecordMetrics uses these methods
to set and get the respective metrics.
"""


class Metrics:
    def __init__(self):
        self.precision = 0.0
        self.recall = 0.0

    def record_precision(self, p):
        self.precision = p

    def record_recall(self, r):
        self.recall = r

    def get_precision(self):
        return self.precision

    def get_recall(self):
        return self.recall


class RecordMetrics:
    def __init__(self):
        self.m = Metrics()

    def record(self, p, r):
        self.m.record_precision(p)
        self.m.record_recall(r)

    def get_metrics(self):
        return self.m


if __name__ == '__main__':
    obj = RecordMetrics()
    obj.record(10.0, 14.0)
