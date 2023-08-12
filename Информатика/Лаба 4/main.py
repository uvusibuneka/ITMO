f = open("task_1.txt", 'r', encoding='utf-8')
a = f.readlines()

for s in a:
    print(s)

stack1 = []
stack2 = []

a = a[1:-1]
res = "<root>\n"

for s in a:
    t = s
    if ": {" in s:
        startInd = s.index('"')
        endInd = s.index('"', startInd + 1)
        key = s[startInd + 1:endInd]
        t = " " * startInd + "<" + key + ">" + s[s.index("{") + 1:]
        stack1.append(key)
    else:
        if ":" in s:
            startInd = s.index('"')
            endInd = s.index('"', startInd + 1)
            key = s[startInd + 1:endInd]
            t = "<" + key + ">" + s[s.index(":") + 1:]
            stack2.append(key)
    
    while "}" in t:
        if(len(stack1) == 1):
             t = s.replace("}", " </" + stack1[0]  + ">\n", 1)
        else:
             t = s.replace("}", " </" + stack1[-1]  + ">\n", 1)
        stack1.pop()

    while "," in t:
        if(len(stack2) == 1):
             t = t.replace(",", " </" + stack2[0]  + ">", 1)
        else:
             t = t.replace(",", " </" + stack2[-1]  + ">", 1)
        stack2.pop()
    startInd = s.index('"')
    t = " " * startInd + t
    res += t            
    
    print(t)

res += "</root>";
print(res)
