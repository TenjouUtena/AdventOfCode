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

    candidates = filter(lambda x: x[0]+1000 > index, candidates)
    curtest = md5(salt + str(index)).hexdigest()
    for _ in xrange(2016):
        curtest = md5(curtest).hexdigest()

    chars = re.findall(fivere, curtest)
    map(lambda x: finals.append(x) if x[1] in chars else None, candidates)
    candidates = filter(lambda x: not (x[1] in chars), candidates)

    m = re.search(threere, curtest)
    if m:
        candidates.append((index, m.group("ch"), curtest))

    index += 1

print sorted(finals)[63]
