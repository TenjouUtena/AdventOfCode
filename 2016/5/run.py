
import hashlib,time

start = time.time()

inp = 'uqwqemis'

salt = 0

found = False

pw = "--------"
itf = 0

while not found:
  if salt % 100000 == 0:
    print salt, pw
  test = hashlib.md5(inp+str(salt)).hexdigest()

  if(test[:5] == "00000"):
    pos = test[5]
    ch = test[6]
    if(ord(pos) < 56):
      numpos = int(pos)
      if pw[numpos] == '-':
        pw = pw[0:numpos] + ch + pw[numpos+1:]
        itf += 1

  if(itf == 8):
    found = True

  salt += 1

print pw

end = time.time()
print(end - start)