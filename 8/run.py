
import re

display = []

for y in xrange(6):
    row = []
    for x in xrange(50):
        row += '.'
    display.append(row)


def rect(display, wide, tall):
    for y in xrange(tall):
        for x in xrange(wide):
            display[x][y] = '#'

def rotrow(display, row, amt):
    display[row] = display[row][-amt:] + display[row][:-amt]

def rotcol(display, col, amt):
    rr = zip(*display)
    newcol = rr[col][-amt:] + rr[col][:-amt]
    x = 0
    for row in display:
        row[col] = newcol[x]
        x += 1

def showDisplay(display):
    for row in display:
        print ''.join(row)
    print

def test():
    rect(display,2,3)
    showDisplay(display)
    rotcol(display, 1, 5)
    showDisplay(display)
    rotrow(display,0,4)
    showDisplay(display)

def run():
    f = open('codes','rb')
    for line in f.readlines():
        match = False
        m1 = re.match(r"rect ([0-9]+)x([0-9]+)",line)
        if m1:
            rect(display, int(m1.group(2)), int(m1.group(1)))
            match = True
        m2 = re.match(r"rotate row y=([0-9]+) by ([0-9]+)",line)
        if m2:
            rotrow(display, int(m2.group(1)), int(m2.group(2)))
            match = True
        m3 = re.match(r"rotate column x=([0-9]+) by ([0-9]+)",line)
        if m3:
            rotcol(display, int(m3.group(1)), int(m3.group(2)))
            match = True
        if not match:
            print "Undecoded Line:", line
    count = 0
    for row in display:
        count += row.count('#')

    showDisplay(display)

    print "Lit Cells:", count


if __name__ == '__main__':
    #test()
    run()

