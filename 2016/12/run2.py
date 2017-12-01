
import re, time


registers = {'a':0, 'b':0, 'c':1, 'd':0}
halt = False
pointer = 0
instructions = []

def value(x):
    if x in registers:
        return registers[x]
    else:
        return x

def cpy(x,y):
    registers[y] = value(x)
def asn(x,y):
    registers[y] = x
def rcp(x,y):
    registers[y] = registers[x]
def inc(x,_):
    registers[x] += 1
def dec(x,_):
    registers[x] -= 1
def jnz(x,y):
    global pointer
    if value(x) != 0:
        pointer += y -1
def adt(x,y):
    registers[x] += registers[y]
def nop(*_):
    pass

def comp():
    inst = {'cpy':cpy, 'inc':inc, 'dec':dec, 'jnz':jnz, 'adt':adt, 'nop':nop, 'asn':asn, 'rcp':rcp}

    f = open('codes','rb')
    #test = re.compile(r"([a-z]{3}) ([a-z0-9]+) ?([\-a-z0-9]+)?")
    for line in f.readlines():
        mm = re.match(r"([a-z]{3}) ([a-z0-9]+) ?([\-a-z0-9]+)?",line)
        if mm.lastindex == 2:
            instructions.append((inst[mm.group(1)],mm.group(2),None))
        else:
            opr1 = mm.group(2) if mm.group(2) in registers else int(mm.group(2))
            opr2 = mm.group(3) if mm.group(3) in registers else int(mm.group(3))
            instructions.append((inst[mm.group(1)],opr1, opr2))
    f.close()

def optimize_adt():
    ## Optimization Pass 1
    ## Add register add instruction
    for xx in xrange(len(instructions)):
        i,a,b = instructions[xx]
        if i == jnz and b == -2 :
            i1,o2,_ = instructions[xx-1]
            i2,o3,_ = instructions[xx-2]
            if o2 == a and i1 == dec and i2 == inc:
                instructions[xx-2] = (adt, o3, a)
                instructions[xx-1] = (asn, 0, a)
                instructions[xx] = (nop, None, None)

def optimize_cpy():
    for x in xrange(len(instructions)):
        i,a,b = instructions[x]
        if i == cpy:
            if a in registers:
                instructions[x] = (rcp, a, b)
            else:
                instructions[x] = (asn, a, b)

def run():
    global pointer, instructions
    while pointer < len(instructions):
        i, a, b = instructions[pointer]
        i(a,b)
        pointer += 1


if __name__ == "__main__":

    comp()
    start = time.time()
    optimize_cpy()
    optimize_adt()
    start = time.time()
    run()
    print registers
    print 'Total Time:', time.time() - start
