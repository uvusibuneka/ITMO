import json
import time


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

start_time = time.time()
for i in range(0, 1000):
    with open("task_1.json", encoding="utf-8") as f:
        data = json.load(f)
        data = json.loads(f.read())
    res = generate(data, 0)
print("<root>" + res + "</root>")
print("Время работы: ", time.time() - start_time)