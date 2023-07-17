import time

start_time = time.time()
errorText = "Некорректный JSON, съешь шоколадку и иди исправлять"


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


def findTag(a, l, r):
    assert a[l] == '"', errorText
    l += 1
    tag = ""
    r = 0
    for i in range(l, len(a)):
        if a[i] == '"':
            r = i
            break
        else:
            tag += a[i]
    assert a[r + 1] == ':' and r + 2 < len(a), errorText
    r += 2
    return [tag, r]

def tryGetBlock(a, l, r):
    global brackets
    if a[l] != '{':
        return {0, 0}

    return [rec(a, l + 1, brackets[l + 1]), brackets[l + 1]]

def tryGetStr(a, l, r):
    if a[l] != '"':
        return [0, 0]
    l += 1
    value = ""
    for i in range(l, r):
        if a[i] == '"':
            l = i + 1
            break
        else:
            value += a[i]
    assert l != r, errorText
    return [value, l]


def tryGetArray(a, l, r):
    if a[l] != '[':
        return [0, 0]
    l += 1
    value = ""
    for i in range(l, r):
        if a[i] == ']':
            l = i + 1
            break
        else:
            value += a[i]
    assert l + 1 != r, errorText
    return [value, l]


def tryGetNum(a, l, r):
    if not a[l].isdigit():
        return [0, 0]
    value = ""
    for i in range(l, r):
        if not a[i].isdigit():
            l = i
            break
        else:
            l = i + 1
            value += a[i]

    return [value, l]


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

def rec(a, l, r):
    answ = dict()
    while l < r:
        tag = findTag(a, l, r)
        l = tag[1]
        assert tag != [0, 0], errorText
        res = tryGetStr(a, l, r)
        if res != [0, 0]:
            answ[tag[0]] = res[0]
        else:
            res = tryGetNum(a, l, r)
            if res != [0, 0]:
                answ[tag[0]] = res[0]
            else:
                res = tryGetArray(a, l, r)
                if res != [0, 0]:
                    answ[tag[0]] = res[0]
                else:
                    res = tryGetBlock(a, l, r)
                    if res != [0, 0]:
                        answ[tag[0]] = res[0]

        assert res != [0, 0], errorText
        l = res[1]
        assert (l == r) or (a[l] == ',') or (a[l] == '}'), errorText
        l += 1
    return answ


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
    json = json[0:]
    json = json.replace("{", "", 1)
    a = rec(json, 0, len(json) - 1)
    res = time.time() - start_time
    answ = generate(a, 0)[1:-1]
print("<root>\n", answ, "\n</root>")
print(f"Время работы: {res:.10f}")
