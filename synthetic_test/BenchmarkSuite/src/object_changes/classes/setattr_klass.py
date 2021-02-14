"""
:testcase_name setattr_klass
:author Sriteja Kummita
:script_type Class
:description Dynamically initializing the class variable with an object in Pokemon class
"""


class Pikachu:
    def talk(self):
        return 'Pikkkachuuu'


class Squirtle:
    def talk(self):
        return 'SquirtleSquirtle'


class Bulbasaur:
    def talk(self):
        return 'Bulbasaaaur'


class Pokemon:
    def __init__(self):
        self.pokemon = None

    def set_pokemon(self, p):
        setattr(self, 'pokemon', p())

    def speak(self):
        return self.pokemon.talk()


if __name__ == '__main__':
    po = Pokemon()
    po.set_pokemon(Bulbasaur)
    po.speak()
    # print(po.speak())
