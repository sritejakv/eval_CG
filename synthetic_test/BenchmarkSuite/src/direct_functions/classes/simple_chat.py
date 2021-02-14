"""
:testcase_name simple_chat
:author Sriteja Kummita
:script_type class
:description This file demonstrates simple chat application. class User represents an end user and
    class Display is used to print the chat messages to the command line. User uses Display to share
    messages.
"""


class Display:
    def __init__(self, user):
        self.user = user

    def display_message(self, message, to):
        print("[{} says to {}]: {}".format(self.user.get_name(), to.get_name(), message))


class User:
    def __init__(self, name, id):
        self.name = name
        self.id = id
        self.chat = Display(self)

    def get_name(self):
        return self.name

    def say(self, message, user):
        self.chat.display_message(message, user)


if __name__ == '__main__':
    alice = User("Alice", 111)
    bob = User("Bob", 112)
    alice.say("Hi! Bob!", bob)

