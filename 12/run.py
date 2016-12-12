
import re

f = open('codes','rb')

registers = {'a':0, 'b':0, 'c':1, 'd':0}

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
def inc(x):
    registers[x] += 1
def dec(x):
    registers[x] -= 1
def jnz(x,y):
    if value(x) != 0:
        pointer += value(y) -1


inst = {'cpy':cpy, 'inc':inc, 'dec':dec, 'jnz':jnz}

instructions = f.readlines()



while not halt:
    mm = re.match(r"([a-z]{3}) ([a-z0-9]+) ?([\-a-z0-9]+)?",instructions[pointer])
    if pas % 100000 == 0:
        print "Stack: ", registers
        print "Instruction: ", instructions[pointer]
    pas += 1
    if not mm:
        print "Bad command:", instructions[pointer]
        continue
    if mm.group(1) == 'cpy':
        if registers.has_key(mm.group(2)):
            registers[mm.group(3)] = registers[mm.group(2)]
        else:
            registers[mm.group(3)] = int(mm.group(2))

    if mm.group(1) == 'inc':
        registers[mm.group(2)] += 1
    if mm.group(1) == 'dec':
        registers[mm.group(2)] -= 1
    
    pointer += 1

    if mm.group(1) == 'jnz':
        if value(mm.group(2)) != 0:
            pointer += (int(mm.group(3))-1)

    if pointer == len(instructions):
        halt = True


print registers



