
import re, time

start = time.time()

f = open('codes','rb')

registers = {'a':0, 'b':0, 'c':0, 'd':0}

halt = False

pointer = 0

pas = 0

def value(x):
    try:
        return int(x)
    except:
        return registers[x]

def cpy(x,y):
    registers[y] = value(x)
def inc(x,y):
    registers[x] += 1
def dec(x,y):
    registers[x] -= 1
def jnz(x,y):
    global pointer
    if value(x) != 0:
        pointer += value(y) -1


inst = {'cpy':cpy, 'inc':inc, 'dec':dec, 'jnz':jnz}

instructions = []

for line in f.readlines():
    mm = re.match(r"([a-z]{3}) ([a-z0-9]+) ?([\-a-z0-9]+)?",line)
    if mm.lastindex == 2:
        instructions.append((inst[mm.group(1)],mm.group(2),None))
    else:
        instructions.append((inst[mm.group(1)],mm.group(2),mm.group(3)))




while not halt:
    i, a, b = instructions[pointer]
    i(a,b)

    pointer += 1
    if pointer == len(instructions):
        halt = True


print registers
print time.time() - start


