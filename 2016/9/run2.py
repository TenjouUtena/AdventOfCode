
import re, time

f = open('codes','rb')
text = ''.join(f.readlines())


def analyze(text):
    mm = re.search(r"\(([0-9]+)x([0-9]+)\)",text)
    retval = 0
    if mm:
        st = len(text[:mm.start()])
        rr = (text[mm.end():mm.end()+int(mm.group(1))])
        dd = analyze(rr) * int(mm.group(2))
        rr = analyze(text[mm.end()+int(mm.group(1)):])
        retval = st+dd+rr
    else:
        retval = len(text)
    return retval

#print analyze("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")
#print analyze("X(8x2)(3x3)ABCY")
#print analyze("(27x12)(20x12)(13x14)(7x10)(1x12)A")
start = time.time()
print analyze(text)
print time.time() - start