import requests
import html_to_json

url = "https://itmo.ru/ru/schedule/0/P3106/schedule.htm"

html = requests.get(url).text


startIndex = html.index("<table id='2day'")
endIndex = html.index("</table>", startIndex)

html = html[startIndex:endIndex+8]


output_json = html_to_json.convert(html)
print(output_json)


