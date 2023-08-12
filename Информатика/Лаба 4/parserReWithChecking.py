import re
import time


def getJson(filename):
    with open(filename, "r", encoding="utf-8") as file:
        res = file.read()
    return res


def errorPrint():
    print("Исходный текст некорректен")
    exit(0)

def check(a):
    if a.count('"') % 2 == 1 or a.count('{') != a.count('}'):
        errorPrint()



def rec(a):
    p = re.compile(r"\"[^\"]*\":\"[^\"]*\"")
    while not p.search(a) is None:
        l = (p.search(a)).start()
        r = p.search(a).end()
        t = a[l + 1:r]
        mid = re.search(r"\":\"", t).start()
        s1 = t[:mid]
        s2 = t[mid + 3:-1]
        a = a[:l] + f"<{s1}>{s2}<\\{s1}>" + a[r:]
    p = re.compile(r"\"[^\"]*\":{(([^{}]*)|(([^{}]*{[^{}]*}[^{}]*)*))}")
    while not p.search(a) is None:
        l = p.search(a).start()
        r = p.search(a).end()
        t = a[l + 1:r]
        mid = re.search(r"\":{", t).start()
        tag = t[:mid]
        a = a[:r - 1] + f"</{tag}>" + a[r:]
        a = a[:l] + f"<{tag}>" + a[l + mid + 4:]
    a = re.sub(r"(?<=>),", "\n", a)
    a = re.sub(r"><", ">\n<", a)
    return a


start_time = time.time()
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
    check(json)
    answ = rec(json)
print("<root>\n" + answ + "\n</root>")
print("Время работы: ", time.time() - start_time)

