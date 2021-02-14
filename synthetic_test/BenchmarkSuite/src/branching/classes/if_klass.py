"""
:testcase_name if_class
:author Sriteja Kummita
:script_type Class
:description Class containing multiple if-statements within each calling 'toggle' method of respective sub-class of the
super class 'Device'.
"""


class Device(object):
    def __init__(self, n):
        self.name = n
        self.state = False

    def toggle_state(self):
        self.state = not self.state


class Light(Device):
    def __init__(self, name, location):
        super().__init__(name)
        self.loc = location


class Fan(Device):
    def __init__(self, name, location):
        super().__init__(name)
        self.loc = location


class BedLamp(Device):
    def __init__(self, name, location):
        super().__init__(name)
        self.loc = location


class ToggleDevices(object):
    def __init__(self, n, loc):
        self.name = n
        self.location = loc

    def toggle(self):
        if self.name == "Light":
            return Light(self.name, self.location).toggle_state()
        elif self.name == "Fan":
            return Fan(self.name, self.location).toggle_state()
        elif self.name == "BedLamp":
            return BedLamp(self.name, self.location).toggle_state()


if __name__ == '__main__':
    obj = ToggleDevices("Light", "Kitchen")
    obj.toggle()
