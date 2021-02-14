"""
:testcase_name language_factory
:author Sriteja Kummita
:script_type class
:description Demonstrates the factory pattern in Python. It contains German and English converters each contain
a method convert. LanguageFactory assigns respective converter based on the parameter passed.
"""


import abc


class Converter:
    @abc.abstractmethod
    def convert(self, text):
        pass


class GermanConverter(Converter):
    def convert(self, text):
        print("Converting to German")


class EnglishConverter(Converter):
    def convert(self, text):
        print("Converting to English")


class LanguageFactory:
    def __init__(self, l):
        self.factory = {'English': EnglishConverter, 'German': GermanConverter}
        self.converter = self.factory[l]()

    def translate(self, plain_text):
        self.converter.convert(plain_text)


if __name__ == '__main__':
    c = LanguageFactory("German")
    c.translate("Hello")
