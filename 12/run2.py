
import re, time

start = time.time()

registers = {'a':0, 'b':0, 'c':0, 'd':0}
halt = False
pointer = 0
pas = 0

def value(x):
    if x in registers:
        return registers[x]
    else:
        return x

def cpy(x,y):
    registers[y] = value(x)
def inc(x,y):
    registers[x] += 1
def dec(x,y):
    registers[x] -= 1
def jnz(x,y):
    global pointer
    if value(x) != 0:
        pointer += y -1


inst = {'cpy':cpy, 'inc':inc, 'dec':dec, 'jnz':jnz}
instructions = []
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

while pointer < len(instructions):
    i, a, b = instructions[pointer]
    i(a,b)
    pointer += 1

print registers
print 'Total Time:', time.time() - start


