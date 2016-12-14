
import re
from hashlib import md5

candidates = []

finals = []

salt = 'yjdafjpo'
#salt = 'abc'
index = 0

threere = r"(?P<ch>[0-9a-f]+)(?P=ch)(?P=ch)"
fivere = r"(?P<ch>[0-9a-f]+)(?P=ch)(?P=ch)(?P=ch)(?P=ch)"

while len(finals) < 64:

    if index % 1000 == 0:
        print index, len(candidates), len(finals)


    newcan = []
    for tt in candidates:
        if tt[0]+1000 > index:
            newcan.append(tt)
    candidates = newcan

    curtest = md5(salt + str(index)).hexdigest()
    for x in xrange(2016):
        curtest = md5(curtest).hexdigest()

    chars = re.findall(fivere, curtest)

    newcan = []
    for tt in candidates:
        if tt[1] in chars:
            r, g, h = tt
            finals.append((r,g,h,index,curtest))
            print (r,g,h,index,curtest)
        else:
            newcan.append(tt)

    candidates = newcan

    m = re.search(threere, curtest)
    if m:
        candidates.append((index, m.group("ch"), curtest))

    index += 1

gg = []

for x in finals:
    gg.append(x[0])

print sorted(gg)

print sorted(gg)[63]
