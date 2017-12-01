import re


class Output(object):
    def __init__(self, tag):
        self.value = -1
        self.tag = tag

    def give(self, value):
        self.value = value

class Bot(object):
    def __init__(self, tag):
        self.values = []
        self.high = None
        self.low = None
        self.tag = tag

    def give(self, value):
        self.values.append(value)
        self.check()

    def assign(self, high, low):
        self.high = high
        self.low = low
        self.check()

    def checkpart1(self):
        if min(self.values) == 17 and max(self.values) == 61:
            print "Bot %d has 17 and 61" % self.tag

    def check(self):
        if len(self.values) > 1 and self.high and self.low:
            self.checkpart1()
            self.high.give(max(self.values))
            self.low.give(min(self.values))


def retobj(typ, i, bots, outputs):
    if typ == "bot":
        if not bots.has_key(i):
            bots[i] = Bot(i)
        return bots[i]
    else:
        if not outputs.has_key(i):
            outputs[i] = Output(i)
        return outputs[i]




if __name__ == "__main__":
    outputs = {}
    bots = {}
    f = open('codes','rb')
    for line in f.readlines():
        match = False
        m1 = re.match(r'value ([0-9]+) goes to bot ([0-9]+)',line)
        if m1:
            v = int(m1.group(1))
            b = int(m1.group(2))
            if not bots.has_key(b):
                bots[b] = Bot(b)
            bots[b].give(v)
            match = True

        m2 = re.match(r'bot ([0-9]+) gives low to (bot|output) ([0-9]+) and high to (bot|output) ([0-9]+)',line)
        if m2:
            b = int(m2.group(1))
            o1 = int(m2.group(3))
            o2 = int(m2.group(5))
            if not bots.has_key(b):
                bots[b] = Bot(b)
            t1 = retobj(m2.group(2), o1, bots, outputs)
            t2 = retobj(m2.group(4), o2, bots, outputs)
            bots[b].assign(t2, t1)
            match = True


        if not match:
            print "Unmatched Line:",line

    print "Part 2:", (outputs[0].value * outputs[1].value * outputs[2].value)
