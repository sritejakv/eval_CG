"""
:testcase_name read_book
:author Sriteja Kummita
:script_type module
:description Contains arbitrary functions between methods simulating read a book.
"""


def read(page):
    print(page)


def open(page_num):
    read(page_num)


def close(book_name):
    pass


def next(page_num):
    open(page_num + 1)


def get_book(book_name):
    return book_name


def read_book(book_name, pages):
    get_book(book_name)
    for page in range(pages):
        open(page)
        read(page)
        next(page)
    close(book_name)


if __name__ == '__main__':
    read_book("HarryPotter", 1120)
