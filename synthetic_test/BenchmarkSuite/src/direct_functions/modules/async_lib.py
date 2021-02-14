"""
:testcase_name async_lib
:author Sriteja Kummita
:script_type Module
:description Program contains async/await statements. There is a function call from 'consumer' to 'sleep' using await
statement. The method 'consumer' takes two parameters.
"""


async def sleep():
    print("Hello")


async def consumer(name, product):
    print(f'Consumer {name}: consuming = {product}\n')
    await sleep()


if __name__ == '__main__':
    try:
        consumer("A", 2).send(None)
    except StopIteration:
        pass
