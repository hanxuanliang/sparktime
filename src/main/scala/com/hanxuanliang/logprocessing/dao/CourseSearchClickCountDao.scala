package com.hanxuanliang.logprocessing.dao

import com.hanxuanliang.logprocessing.domain.{CourseClickCount, CourseSearchClickCount}
import com.hanxuanliang.logprocessing.util.HBaseUtils
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.mutable.ListBuffer

object CourseSearchClickCountDao {
  val TABLENAME = "logcourse_search_clickcount"
  val CF = "info"
  val QUALIFER = "click_count"

  def save(list: ListBuffer[CourseSearchClickCount]): Unit = {
    val table = HBaseUtils.getInstance().getTable(TABLENAME)
    for(ele <- list) {
      table.incrementColumnValue(
        Bytes.toBytes(ele.day_search_course),
        Bytes.toBytes(CF), Bytes.toBytes(QUALIFER), ele.click_count)
    }
  }

  def count(day_search_course: String): Long = {
    val table = HBaseUtils.getInstance().getTable(TABLENAME)

    val get = new Get(Bytes.toBytes(day_search_course))
    val value = table.get(get).getValue(CF.getBytes, QUALIFER.getBytes)

    if (value == null) 0L
    else Bytes.toLong(value)
  }

  def main(args: Array[String]): Unit = {
    val list = new ListBuffer[CourseSearchClickCount]
    list.append(CourseSearchClickCount("20190703_www.baidu.com_8", 8))
    list.append(CourseSearchClickCount("20190703_cn.bing.com_9", 9))

    save(list)
    print(count("20190703_www.baidu.com_8") + " : " +
      count("20190703_cn.bing.com_9"))
  }
}
