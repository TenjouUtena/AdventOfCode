
import re

f = open('codes','rb')
text = ''.join(f.readlines())

#text = "X(8x2)(3x3)ABCY"

output = ""
done = False
rtext = text
while not done:
    mm = re.search(r"\(([0-9]+)x([0-9]+)\)",rtext)
    if mm:
        ## Process blanks
        #print mm, rtext, rtext[:mm.start()], rtext[mm.end():mm.end()+int(mm.group(1))], rtext[mm.end()+int(mm.group(1)):]
        output += rtext[:mm.start()]
        output += (rtext[mm.end():mm.end()+int(mm.group(1))] * int(mm.group(2)))
        rtext = rtext[mm.end()+int(mm.group(1)):]

    else:
        output += rtext
        done = True

print output, len(output)