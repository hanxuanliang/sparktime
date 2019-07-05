const source_json = [
  {"name":"基于Storm构建实时热力分布项目实战","value":318},
  {"name":"商业级支付宝小程序入门与实战","value":59},
  {"name":"Zookeeper源码分析","value":59},
  {"name":"精讲Elastic-job + Quartz实现企业级定时任务","value":62},
  {"name":"Python前后端分离开发Vue+Django REST framework实战","value":327},
  {"name":"Spark Streaming实时流处理项目实战","value":329},
  {"name":"以慕课网日志分析为例 进入大数据Spark SQL的世界","value":288},
  {"name":"深度学习之神经网络核心原理与算法","value":330},
  {"name":"基于Spring Cloud微服务架构 广告系统设计与实现","value":43},
  {"name":"Node.js开发仿知乎服务端 深入理解RESTful API","value":57},
  {"name":"阿里新零售数据库设计与实战","value":51},
  {"name":"Node.js入门到企业Web开发中的应用","value":355},
  {"name":"Google老师亲授 TensorFlow2.0 入门到进阶","value":62},
  {"name":"编程必备基础知识 计算机组成原理+操作系统+计算机网络","value":52},
  {"name":"学习Scala进击大数据Spark生态圈","value":330},
  {"name":"Angular8开发拼多多webapp 从基础到项目实战","value":38},
  {"name":"全流程开发 GO实战电商网站高并发秒杀系统","value":64},
  {"name":"Spring Cloud Alibaba微服务从入门到进阶","value":39},
  {"name":"强力Django + 杀手级xadmin开发在线教育网站","value":56},
  {"name":"Spring Boot仿抖音短视频小程序开发 全栈式实战项目","value":55},
  {"name":"10小时入门大数据","value":323},
  {"name":"Python3实战Spark大数据分析及调度","value":278},
  {"name":"Java Web自动化测试 Selenium基础到企业实际应用","value":32}
]

for(let index of source_json) {
  console.log('\"' + index['name'] + '\",')
}