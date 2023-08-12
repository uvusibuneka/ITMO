import requests


r = requests.get("https://itmo.ru/ru/schedule/0/P3106/schedule.htm")
html = r.text
print(r.text)
startInd = html.index("<table id='2day'")
endInd = html.index("</table>", startInd)
print(startInd, endInd)

res = "<div>" + html[startInd:endInd + 8] + "</div>"

print(res)
