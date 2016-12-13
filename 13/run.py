
import itertools

NUMBER = 1362

def hammy(num):
    count = 0
    while num:
        num &= num-1
        count+=1

    return count

def valid_node(node):
    x,y = node
    if x < 0 or y < 0:
        return False
    val = x**2 + y**2 + 3*x + 2*x*y + y + NUMBER

    return not (hammy(val) % 2)


def get_neighbors(node):
    x,y = node
    ntest = [(x-1,y), (x,y-1), (x+1,y), (x,y+1)]

    return filter(valid_node, ntest)

def dist(start, end):
    sx, sy = start
    ex, ey = end
    return abs(ex-sx) + abs(ey-sy)

def find_lowest(F, ope):
    d = 9999999999
    r = None
    for k in ope:
        i = F[k]
        if i < d:
            r = k
            d = i
    return r


def a_star(start, end):
    closed = []
    ope = [start]

    G = {}
    F = {}

    G[start] = 0
    F[start] = dist(start, end)

    while len(ope) > 0:
        current = find_lowest(F, ope)
        if current == end:
            return G[end]

        closed.append(current)
        ope.remove(current)
        curScore = G[current] + 1
        for node in get_neighbors(current):
            if node in closed:
                continue
            if node not in ope:
                ope.append(node)
            elif curScore >= G[node]:
                continue

            G[node] = curScore
            F[node] = curScore + dist(node, end)
    return None

def main():
    print "Part 1:", a_star((1,1), (31,39))

    visit = 0
    for node in itertools.product(range(54),repeat=2):
        if not valid_node(node):
            continue
        if dist((1,1),node) > 50:
            continue
        p =  a_star((1,1), node)
        if p and p <=50:
            visit +=1

    print "Part 2:", visit+1



if __name__ == "__main__":
    main()


