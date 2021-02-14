"""
:testcase_name user_credentials
:author Sriteja Kummita
:script_type Class
:description Implementation using decorators in Python. Decorator 'validate_password' check the validity of the first
argument passed to the function being invoked. 'set_password' is annotated with the decorator 'validate_password' to
check validate if the first argument passed is of a valid password or not
"""


def validate_password(func):
    def wrapper(*args, **kwards):
        pwd = args[1]
        not_accepted = ['-', '/', '^']
        if not any(x in pwd for x in not_accepted):
            return func
        else:
            print("Invalid password")
    return wrapper


class User:
    def __init__(self):
        self.user_name = None
        self.password = None

    def set_username(self, username):
        self.user_name = username

    @validate_password
    def set_password(self, pwd):
        self.password = pwd


if __name__ == '__main__':
    user = User()
    user.set_username("Lorem")
    user.set_password("dfsAfs/d98o3")
