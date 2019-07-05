# coding=UTF-8
import random
import time

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
  "learn/928",
  "course/list"
]

ip_splices = [
  132, 156, 124, 10 ,29, 167, 143, 187, 30 ,46, 55, 63, 72, 87, 98, 168
]

http_referers = [
  "http://www.baidu.com/s?wd={query}",
  "https://www.sogou.com/web?query={query}",
  "http://cn.bing.com/search?q={query}",
  "https://search.yahoo.com/search?p={query}",
  "https://www.google.com/search?q={query}",
  "https://www.so.com/s?q={query}",
  "https://so.m.sm.cn/s?q={query}"

]

search_keyword = [
  "Spark SQL实战",
  "Hadoop基础",
  "Storm实战",
  "Spark Streaming实战",
  "Spring Cloud Alibaba微服务从入门到进阶",
  "阿里新零售数据库设计与实战",
  "基于Spring Cloud微服务架构 广告系统设计与实现",
  "Spring Boot仿抖音短视频小程序开发 全栈式实战项目",
  "从0开始 独立完成企业级Java电商网站开发(服务端)",
  "从0开始 独立完成企业级Java电商网站开发(服务端)",
  "精讲Elastic-job + Quartz实现企业级定时任务",
  "React劲爆新特性Hooks 重构去哪儿网火车票PWA",
  "Hadoop基础与电商行为日志分析 新手入门大数据",
  "纯正商业级应用 Node.js Koa2开发微信小程序服务端",
  "Python Flask构建微信小程序订餐系统",
  "前端下一代开发语言TypeScript  从基础到axios实战",
  "深度剖析HashMap源码，HashMap面试可与面试官正面硬杠",
  "全栈进阶课程 React16.8+Next.js+Koa2一步到位开发Github",
  "基础入门 全角度解读企业主流数据库MySQL8.0",
  "跳槽及毕设Django高级实战教程 企业级问答网站开发",
  "JavaScript版 数据结构与算法",
  "剖析Framework面试 冲击Android高级职位",
  "以慕课网日志分析为例  进入大数据Spark SQL的世界",
  "Java电商秒杀系统深度优化 从容应对亿级流量挑战",
  "商业级支付宝小程序入门与实战",
  "全流程开发 GO实战电商网站高并发秒杀系统",
  "Angular8开发拼多多webapp 从基础到项目实战",
  "大数据面试"
]

status_codes = [
  "200", "404", "500"
]

def sample_url():
  # 因为 random.sample(c, 5) 返回的是 以5个元素的数组，所以需要 [1] 取1个元素
  return random.sample(url_paths, 1)[0]

def sample_ip():
  slice = random.sample(ip_splices, 4)
  return ".".join([str(item) for item in slice])

def sample_referer():
  if random.uniform(0, 1) > 0.2:
    return "-"
  
  referer_str = random.sample(http_referers, 1)
  query_str = random.sample(search_keyword, 1)
  
  return referer_str[0].format(query=query_str[0])

def sample_status_code():
  return random.sample(status_codes, 1)[0]

def generate_log(count=10):
  time_str = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())

  f = open("/home/hadoop/data/project/logs/access.log", "w+")
  # 文件如果存在就追加 
  # f = open("/home/hadoop/data/project/logs/access.log", "a+")

  while count >= 1:
    query_log = "{local_time}\t{url}\t{ip}\t{referer}\t{status_code}".format(
      local_time=time_str, url=sample_url(), ip=sample_ip(), 
      referer=sample_referer(), status_code=sample_status_code())
    
    f.write(query_log + "\n")
    count = count - 1

if __name__ == "__main__":
  generate_log(10)
