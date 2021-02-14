"""
:testcase_name while_klass
:author Sriteja Kummita
:script_type Class
:description Class demonstrating a function call inside a while-loop. For each item in the parameter,
'remove_line_breaks' is called to strip out the '\n' string from each item.
"""
import os


class RemoveLineBreaks(object):
    def __init__(self, s):
        self.string = s
        self.line_list = []

    def read_string(self):
        index = len(self.string)
        i = 0
        while i < index:
            modified_line = self.remove_line_breaks(self.string[i])
            self.line_list.append(modified_line)
            i += 1
        return self.line_list

    def remove_line_breaks(self, s):
        s = s.replace("\n", "")
        return s


if __name__ == '__main__':
    obj = RemoveLineBreaks(["Lorem\nIpsum", "Lorem\nIpsum", "Lorem\nIpsum", "Lorem\nIpsum"])
    obj.read_string()
