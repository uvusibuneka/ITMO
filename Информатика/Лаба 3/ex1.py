#---------------------------------------
# Требуется найти количество смайликов ":-{O"(без кавычек) в предложенном тексте
#---------------------------------------


import re

def findSmile(s):
    print("Введите смайлик")
    sm = input()
    smile = re.escape(sm)
    p = re.compile(smile) # паттерн указанного смайлика
    result = p.findall(s) # находим все вхождения смайлика в строке
    print(len(result))
    
'''
findSmile("dksjfdsjkfhjdhfj:skdjsk:d{Ojjdhjd:-{Okkdfjdkfj:-{O")
findSmile("fgjdkfhkhjdkgsdhdhgjfghYfTDT::S>SX:SS:LDKKKKSLDDK:S{OFJ")
findSmile(":-{O}-:..:-O{}")
findSmile(":{u:sOo-yOc-g:e:nOo:k{a:q:i-iOb-q:j-f-h{i{s{uOp-uOvOaOo:j-iOo:-{Od-yOlOo:x{h{yOx{l-q{y:x:m-pOt{o:o-a:m:a:d-l{pOaOp:j-o{u:j-m-uOo{p-a{o{h{oOb-a-r{gOx-kOf-p{w{r{cOd{p-v-g-d{n{i{fOd{r-o:y-xOyOb{bOv-k:q{q:h-y")

'''
print("Введите текст, чтобы узнать количество вхождений смайла. Для окончания ввода введите пустую строку.")
s = ""
t = input()
while(t != ""):
    s += t
    t = input()

findSmile(s)

#Tест1":dksjfdsjkfhjdhfj:skdjsk:d{Ojjdhjd:-{Okkdfjdkfj:-{O"

#Вывод: 2

#Tест2 ":fgjdkfhkhjdkgsdhdhgjfghYfTDT::S>SX:SS:LDKKKKSLDDK:S{OFJ"

#Вывод 0

#Tест3 ":-{O}-:..:-O{}"

#Вывод 1

#Tест4 ":{u:sOo-yOc-g:e:nOo:k{a:q:i-iOb-q:j-f-h{i{s{uOp-uOvOaOo:j-iOo:-{Od-yOlOo:x{h{yOx{l-q{y:x:m-pOt{o:o-a:m:a:d-l{pOaOp:j-o{u:j-m-uOo{p-a{o{h{oOb-a-r{gOx-kOf-w{r{cOd{p-v-g-d{n{i{fOd{r-o:y-xOyOb{bOv-k:q{q:h-y"

#Вывод 1

