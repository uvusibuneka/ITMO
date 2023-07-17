import time
import re

start_time = time.time()
errorText = "Некорректный JSON, съешь шоколадку и иди исправлять"


def getJson(filename):
    with open(filename, "r", encoding="utf-8") as file:
        res = file.read()
    return res


def errorPrint():
    print("Исходный текст некорректен")
    exit(0)

def getIndOfBrackets(a):
    stack = []
    brackets = dict()
    quotes = 0
    for i in range(0, len(a)):
        if a[i] == '"':
            quotes = (quotes + 1) % 2
            continue
        if quotes == 0 and a[i] == "{":
            stack.append(i)
        if quotes == 0 and a[i] == "}":
            brackets[stack[-1]] = i
            stack.pop()
    return brackets


def convert(a):
    p = re.compile(r"\"[^\"]*\":{")
    while True:
        ma = p.search(a)
        if ma is None:
            break
        r = brackets[ma.end()]
        a = a[:r] + f"</{ma.group()}>" + a[r + 1:]
        a = re.sub(ma.group(), f"<{ma.group()[1:-3]}>", a)

    return a



for i in range(0, 100):
    text = getJson("task_1.txt")
    json = ""
    for i in range(0, len(text)):
        if text[i] == '\n':
            continue
        if text[i] != " ":
            json += text[i]
        else:
            if text[:i].count('"') % 2 == 1:
                json += text[i]

    #json = json.replace('\n', '')

    if (len(json) < 2) or (json[0] != "{") or (json[-1] != "}"):
        errorPrint()

    brackets = getIndOfBrackets(json)
    json = json[1:-1]
    print(json, "- это!")
    a = convert(json)
    res = time.time() - start_time
print("<root>\n", a, "\n</root>")
print(f"Время работы: {res:.10f}")
