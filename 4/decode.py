

import re, string


def calccode(code):
    db = {}
    for letter in list(code):
        if letter == '-':
            continue
        if db.has_key(letter):
            db[letter] += 1
        else:
            db[letter] = 1
    occurs = db.values()
    occurs.sort(reverse=True)
    retc = ""
    while len(retc) < 5:
        cand = ""
        f = occurs[0]
        for kk in db.keys():
            if db[kk] == f:
                cand += kk
        cc = list(cand)
        cc.sort()
        for kk in cc:
            del db[kk]
        occurs = db.values()
        occurs.sort(reverse=True)
        retc += ''.join(cc)
    return retc[:5]


def decrypt(codename, id):
    base = '-'
    code = ' '

    base += string.ascii_lowercase
    code += string.ascii_lowercase[id % 26:] + string.ascii_lowercase[:id % 26]
    tab = string.maketrans(base,code)
    return codename.translate(tab)




f = open('codes','rb')

valid = 0

for line in f.readlines():
    mm = re.match(r"([a-z\-]+)([0-9]+)\[([a-z]{5})\]", line)
    if calccode(mm.group(1))==mm.group(3):
        valid += int(mm.group(2))
        name = decrypt(mm.group(1),int(mm.group(2)))
        #print name.split()
        if 'north' in name:
            print name, int(mm.group(2))

print "Total Valid: %d" % valid



