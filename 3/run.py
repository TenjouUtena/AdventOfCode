

f = open('triangles','rb')

fake = f.readlines()
## PART 1:

tel = []
for line in fake:
    ll = line.split(' ')
    tr = []
    for mem in ll:
        if mem != '':
            tr.append(int(mem))
    gg = max(tr)
    tr.pop(tr.index(gg))
    if(reduce(lambda x, y: x+y,tr) > gg):
        tel.append(gg)

print len(tel)


## PART 2

tel = []
t1=[]
t2=[]
t3=[]
cycle = 1
for line in fake:
    ll = line.split(' ')
    tr = []
    for mem in ll:
        if mem != '':
            tr.append(int(mem))
    t1.append(tr[0])
    t2.append(tr[1])
    t3.append(tr[2])
    if(cycle == 3):
        for tri in [t1, t2, t3]:
            gg = max(tri)
            tri.pop(tri.index(gg))
            if(reduce(lambda x,y: x+y, tri) > gg):
                tel.append(gg)
        t1=[]
        t2=[]
        t3=[]
        cycle=1
    else:
        cycle += 1

print len(tel)
