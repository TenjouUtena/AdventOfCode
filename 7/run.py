
import re

f = open('codes','rb')

ips = 0

for line in f.readlines():
    m1 = re.search(r"\[(\w*((?P<ch1>\w)(?!(?P=ch1){2})(?P<ch3>\w)(?P=ch3)(?P=ch1))\w*)\]",line)
    if m1:
        continue
    m2 = re.search(r"(\w*((?P<ch1>\w)(?!(?P=ch1){2})(?P<ch3>\w)(?P=ch3)(?P=ch1))\w*)",line)
    if m2:
        ips += 1

print "Part 1",ips

f.close()

f = open('codes','rb')

ips = 0

for line in f.readlines():
    m1 = re.search(r"((?P<ch1>\w)(?!(?P=ch1))(?P<ch2>\w)(?P=ch1))\w*(\w*\[\w*\]\w*)*\w*\[\w*(?P=ch2)(?P=ch1)(?P=ch2)\w*\]",line)
    if m1:
        ips += 1
        continue
    m2 = re.search(r"\[\w*((?P<ch1>\w)(?!(?P=ch1))(?P<ch2>\w)(?P=ch1))\w*\](\w*\[\w*\]\w*)*\w*(?P=ch2)(?P=ch1)(?P=ch2)",line)
    if m2:
        ips += 1
        continue

print "Part 2", ips