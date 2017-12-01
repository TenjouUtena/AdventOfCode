


movemap1 = { 
  '1': {'D':'4', 'R':'2'},
  '2': {'D':'5', 'R':'3', 'L':'1'},
  '3': {'L':'2', 'D':'6'},
  '4': {'U':'1', 'D':'7', 'R':'5'},
  '5': {'U':'2', 'D':'8', 'L':'4', 'R':'6'},
  '6': {'U':'3', 'D':'9', 'L':'5'},
  '7': {'U':'4', 'R':'8'},
  '8': {'U':'5', 'L':'7', 'R':'9'},
  '9': {'U':'6', 'L':'8'}
}

movemap2 = {
    '1': {'D':'3'},
    '2': {'R':'3', 'D':'6'},
    '3': {'U':'1', 'D':'7', 'L':'2', 'R':'4'},
    '4': {'D':'8', 'L':'3'},
    '5': {'R':'6'},
    '6': {'U':'2', 'D':'A', 'L':'5', 'R':'7'},
    '7': {'U':'3', 'D':'B', 'L':'6', 'R':'8'},
    '8': {'U':'4', 'D':'C', 'L':'7', 'R':'9'},
    '9': {'L':'8'},
    'A': {'U':'6', 'R':'B'},
    'B': {'U':'7', 'D':'D', 'L':'A', 'R':'C'},
    'C': {'U':'8', 'L':'B'},
    'D': {'U':'B'}

}

movemap = movemap2

location = '5'
code = []


f = open('realcode','rb')

for line in f.readlines():
    for x in range(len(line)):
        if line[x] in movemap[location].keys():
            location = movemap[location][line[x]]
    code.append(location)


print code