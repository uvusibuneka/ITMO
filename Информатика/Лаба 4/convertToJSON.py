
stack = []

def nextStr(s):
    nS =  s[s.index(">"):]
    if len(nS) > 1:
        return s[s.index(">") + 1:]
    else:
        return ""

def convert(s):
    answ = ""
    while(s != ""):
        if(s[0] == ">" or (not "<" in s)):
            return "Error"
        if(s[0] != "<"):
            answ += s[:s.find("<")]
            s = s[s.find("<"):]
            
        s = s[1:]
        t = s[:s.find(">")].split()

        answ += "\n" + t[0] + " : { " + "\n"

        for i in range(1, len(t)):
            if not("=" in t[i]):
                return "Error"
            else:
                w = t[i].split("=")
                if(len(w) > 2):
                    return "Error"
                answ += '"' + w[0] + '" : ' + w[1] + "\n" 

        
        print(s)
        s = nextStr(s)
    return answ;
    
s = 'firstString'
htm = ""
while s != "":
    s = input()
    htm += s

print(htm)
print(convert(htm))

