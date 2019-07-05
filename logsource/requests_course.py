from requests_html import HTMLSession
session = HTMLSession()

basic_url = 'https://coding.imooc.com/{}'

url_paths = [
  "class/112.html",
  "class/153.html",
  "class/249.html",
  "class/215.html",
  "class/172.html",
  "class/146.html",
  "class/145.html",
  "class/131.html",
  "class/128.html",
  "class/217.html",
  "class/361.html",
  "class/368.html",
  "class/358.html",
  "class/355.html",
  "class/353.html",
  "class/359.html",
  "class/354.html",
  "class/310.html",
  "class/336.html",
  "class/341.html",
  "class/347.html",
  "class/344.html",
  "class/293.html",
]

for path in url_paths:
  res = session.get(basic_url.format(path))
  about = res.html.find('div.course-infos-top ', first=True)
  title = about.text.split("/")[2].split("\n")[0].strip()
  print("courses.put(\"" + path[6:9] + "\", " + "\"" + title + "\");")
