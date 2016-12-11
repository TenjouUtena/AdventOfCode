
import re, itertools

class Chip(object):
    def __init__(self, typ):
        self.typ = typ
        self.k = "C"

class Gen(object):
    def __init__(self, typ):
        self.typ = typ
        self.k = "G"

class State(object):
    def __init__(self):
        self.floors = [[],[],[],[]]
        self.elevator = 0

    def counttype(self, floor):
        chips = 0
        gens = 0
        for o in self.floors[floor]:
            if o.k == "C":
                chips += 1
            else:
                gens += 1
        return (chips, gens)

    def copystate(self, fro):
        self.elevator = fro.elevator
        ff = 0
        for f in fro.floors:
            for obj in f:
                self.floors[ff].append(obj)
            ff += 1

    def is_same(self, st):
        if self.elevator != st.elevator:
            return False
        for x in xrange(3):
            if len(self.floors[x]) != len(st.floors[x]):
                return False
            c1, g1 = self.counttype(x)
            c2, g2 = st.counttype(x)
            if c1 != c2 or g1 != g2:
                return False
        return True



    def move(self, obj, floor):
        for f in self.floors:
            if obj in f:
                f.remove(obj)
        self.floors[floor].append(obj)

    def is_valid(self):
        v = True
        for f in self.floors:
            for obj in f:
                if obj.k == "C":
                    is_safe = False
                    is_blow = False
                    ## Check everything else
                    for o2 in f:
                        if o2.typ == obj.typ and o2.k == "G":
                            is_safe = True
                        if o2.k == "G":
                            is_blow = True
                    if not is_safe and is_blow:
                        v = False
                        break
        return v


    def is_won(self):
        if len(self.floors[0]) == 0 and len(self.floors[1]) == 0 and len(self.floors[2]) == 0:
            return True
        return False

    def valid_next(self):
        vstates = []
        vmo = []
        for x in xrange(min(len(self.floors[self.elevator]),2)):
            vmo += itertools.combinations(self.floors[self.elevator],x+1)
        for mov in vmo:
            if self.elevator-1 >= 0:
                st = State()
                st.copystate(self)
                st.elevator = self.elevator-1
                for o in mov:
                    st.move(o, st.elevator)
                vstates.append(st)

            if self.elevator+1 <= 3:
                st = State()
                st.copystate(self)
                st.elevator = self.elevator +1
                for o in mov:
                    st.move(o, st.elevator)
                vstates.append(st)
        return filter(lambda x: x.is_valid(), vstates)

    def valid_next_fast(self):
        vstates = []
        #vmo = []
        #for x in xrange(min(len(self.floors[self.elevator]),2)):
        #    vmo += itertools.combinations(self.floors[self.elevator],x+1)
        #for mov in vmo:
        ## If we can move down
        if self.elevator-1 >= 0:
            ## We only ever want to move one object down.
            for obj in self.floors[self.elevator]:
                st = State()
                st.copystate(self)
                st.elevator = self.elevator-1
                st.move(obj, st.elevator)
                vstates.append(st)

        if self.elevator+1 <= 3:
            ## obj to move
            objtomove = min(len(self.floors[self.elevator]),2)
            vmo = itertools.combinations(self.floors[self.elevator],objtomove)
            for mov in vmo:
                st = State()
                st.copystate(self)
                st.elevator = self.elevator +1
                for o in mov:
                    st.move(o, st.elevator)
                vstates.append(st)
        retc = filter(lambda x: x.is_valid(), vstates)
        if len(retc) == 0 and self.elevator < 3:
            vstates = []
            for obj in self.floors[self.elevator]:
                st = State()
                st.copystate(self)
                st.elevator = self.elevator+1
                st.move(obj, st.elevator)
                vstates.append(st)
            return filter(lambda x: x.is_valid(), vstates)
        else:
            return retc

    def show(self):
        print "Elevator:", self.elevator
        for f in self.floors:
            print "Floor: ", f


def find_depth_fast(initial_state):
    tested = []
    roundset = [initial_state]
    done = False
    counter = 0
    while not done:
        print "Trying Round:", counter
        newround = []
        for st in roundset:
            newround += st.valid_next_fast()
        newround2 = []
        for st in newround:
            found = False
            for st2 in tested:
                if st.is_same(st2):
                    found = True
            if not found:
                newround2.append(st)
                tested.append(st)
        roundset = newround2
        counter += 1
        done = any([x.is_won() for x in roundset])

        if len(roundset) == 0:
            print "Bad state!"
            break

    return counter

def find_depth(initial_state):
    tested = []
    roundset = [initial_state]
    done = False
    counter = 0
    while not done:
        print "Trying Round:", counter
        newround = []
        for st in roundset:
            newround += st.valid_next()
        newround2 = []
        for st in newround:
            found = False
            for st2 in tested:
                if st.is_same(st2):
                    found = True
            if not found:
                newround2.append(st)
                tested.append(st)
        roundset = newround2
        counter += 1
        done = any([x.is_won() for x in roundset])

    return counter



def maintest():
    s = State()
    s.floors[0].append(Chip('H'))
    s.floors[0].append(Chip('L'))
    s.floors[1].append(Gen('H'))
    s.floors[2].append(Gen('L'))
    print find_depth_fast(s)



def main():
    s = State()
    s.floors[0].append(Gen('Pol'))
    s.floors[0].append(Gen('Thu'))
    s.floors[0].append(Gen('Pro'))
    s.floors[0].append(Gen('Rut'))
    s.floors[0].append(Chip('Thu'))
    s.floors[0].append(Chip('Rut'))
    s.floors[0].append(Gen('Col'))
    s.floors[0].append(Chip('Col'))
    s.floors[1].append(Chip('Pol'))
    s.floors[1].append(Chip('Pro'))
    s.floors[0].append(Gen('1Col'))
    s.floors[0].append(Chip('1Col'))
    s.floors[0].append(Gen('2Col'))
    s.floors[0].append(Chip('2Col'))
    print find_depth_fast(s)

if __name__ == '__main__':
    main()