import time

start_time = time.time()
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
    quotes = 0
    brackets = 0
    length = len(a)
    res = dict()
    i = 0
    l = 1
    while i < length:
        if a[i] == '"':
            quotes += 1
        if a[i] == '{':
            brackets += 1
        if a[i] == '}':
            brackets -= 1
        if brackets < 0:
            errorPrint()
        if a[i] == ':' and quotes % 2 == 0:
            mid = i
            if i + 1 == length:
                errorPrint()
            match a[i + 1]:
                case '"':
                    quotes += 1
                    i += 2
                    while i < length and a[i] != '"':
                        i += 1
                    if i == length:
                        errorPrint()
                    quotes += 1
                    res[a[l: mid - 1]] = a[mid + 2:i] # тут тоже
                    l = i + 3
                    if i + 1 != length and a[i + 1] != ",":
                        errorPrint()
                case '{':
                    i += 2
                    brackets += 1
                    while i < length:
                        if a[i] == '{':
                            brackets += 1
                        if a[i] == '}':
                            brackets -= 1
                        if a[i] == '"':
                            quotes += 1
                        if a[i] == '}' and brackets == 0:
                            break
                        i += 1
                    if i == length:
                        errorPrint()
                    res[a[l: mid - 1]] = rec(a[mid + 2:i]) # придумай как это оптимизировать
                    l = i + 2
                    if i + 1 != length and a[i + 1] != ",":
                        errorPrint()

                case _:
                    errorPrint()
        i += 1
    return res


for i in range(0, 1000):
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
    a = rec(json)

    res = time.time() - start_time
    answ = generate(a, 0)[1:-1]
print("<root>\n", answ, "\n</root>")
print(f"Время работы: {res:.10f}")
