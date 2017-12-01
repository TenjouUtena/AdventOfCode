

f = open('codes','rb')


code = [{},{},{},{},{},{},{},{}]

for line in f.readlines():
    if len(line) < 8:
        continue
    for x in xrange(8):
        if not code[x].has_key(line[x]):
            code[x][line[x]] = 1
        else:
            code[x][line[x]] += 1

pw = ""
for x in xrange(8):
    mx = 9999
    lt = ''
    for k,v in code[x].iteritems():
        if v < mx:
            mx = v
            lt = k
    pw += lt


print pw


