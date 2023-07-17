import re

s = input()
s = re.sub(r"<","\n<", s)
s = re.sub(r"}","\n}", s)
s = re.sub(r"{","\n{\n", s)
s = re.sub(r",",",\n", s)
print(s)
