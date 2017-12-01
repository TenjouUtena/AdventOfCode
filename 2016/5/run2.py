##test

import time, hashlib, multiprocessing


def worker(start):
    ret = []
    for x in xrange(start*1000000,(start+1)*1000000):
        ff = hashlib.md5("uqwqemis" + str(x)).hexdigest()
        if ff.startswith("00000"):
            ret.append((x,ff[:7]))
    return ret

if __name__ == "__main__":

    multiprocessing.freeze_support()
    start = time.time()
    p = multiprocessing.Pool(10)
    res = p.map(worker, xrange(28))

    resd = {}
    for result in res:
        for seed, output in result:
            resd[seed] = output

    pas = "--------"
    ss = sorted(resd.keys())
    for obj in ss:
        pos = resd[obj][5]
        ch = resd[obj][6]
        if ord(pos) < 56:
            pnum = int(pos)
            if pas[pnum] == '-':
                pas = pas[0:pnum] + ch + pas[pnum+1:]

    print pas
    end = time.time()
    print end-start