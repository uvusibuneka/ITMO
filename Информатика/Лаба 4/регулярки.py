import re

def getJson(filename):
    with open(filename, "r", encoding="utf-8") as file:
        res = file.read()
    return res


def errorPrint():
    print("Исходный текст некорректен")
    exit(0)

def generate(a, t):
        t += 1
        if type(a) == str:
            return a

        if type(a) == dict:
            res = ""
            for x in a.items():
                g = generate(x[1], t)
                if type(g) == str:
                    res += "\n" + " " * t + f"<{x[0]}>" + g + f"</{x[0]}>"
                else:
                    res += "\n" + " " * t + f"<{x[0]}>" + g + " " * t + f"</{x[0]}>\n"
            return res + "\n"

def rec(a):
        p = re.compile(r"\"[^\"]*\":\"[^\"]*\"")
        while not p.search(a) is None:
            l = (p.search(a)).start()
            r = p.search(a).end()
            t = a[l + 1:r]
            mid = re.search(r"\":\"", t).start()
            s1 = t[:mid]
            s2 = t[mid + 3:-1]
            a = a[:l] + f"<{s1}>{s2}<\\{s1}>" + a[r + 1:]

        p = re.compile(r"\"[^\"]*\":{(([^{}]*)|(([^{}]*{[^{}]*}[^{}]*)*))}")
        while not p.search(a) is None:
            x = p.search(a)
            l = x.start()
            r = x.end()
            t = a[l:r]
            mid = re.search(r"\":{", t).start()
            tag = t[1:mid]
            a = a[:r] + f"<\{tag}>" + a[r + 1:]
            a = a[:l] + f"<{tag}>" + a[mid + 3:]
            print(a)
        return a


text = getJson("task_1.txt")
json = ""
for i in range(0, len(text)):
    if text[i] != " ":
        json += text[i]
    else:
        if text[:i].count('"') % 2 == 1:
            json += text[i]

json = json.replace('\n', '')

if (len(json) < 2) or (json[0] != "{") or (json[-1] != "}"):
    errorPrint()

json = json[1:-1]
print(rec(json))
