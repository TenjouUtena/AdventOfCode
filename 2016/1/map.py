import re

f = open('Map File','rb')
ll = f.readline()
ma = ll.split(',')

dirs = {'N':(0,-1), 'S':(0,1),'E':(1,0),'W':(-1,0)}
turns = {'R':{'N':'E','E':'S','S':'W','W':'N'}, 'L':{'N':'W','W':'S','S':'E','E':'N'}}
d = 'N'
loc = [0,0]
visited = []

for m in ma :
    sol = re.match(r"\W*([RL])([0-9]+)",m)
    distance = sol.group(2)
    d = turns[sol.group(1)][d]
    change = map(lambda x : x*int(distance), dirs[d])

    ### Part 1
    loc = [loc[j] + change[j] for j in range(2)]
    print m, d, turn, distance, change, loc, visited


"""
    ## Part 2
    for xx in range(int(distance)):
        if d == 'N':
            loc[1] -= 1
        if d == 'S':
            loc[1] += 1
        if d == 'W':
            loc[0] -= 1
        if d == 'E':
            loc[0] += 1
        if(tuple(loc) in visited):
            print "Visited Twice"
            print loc
        visited.append(tuple(loc))
"""

