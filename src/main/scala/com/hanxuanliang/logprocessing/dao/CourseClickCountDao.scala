package com.hanxuanliang.logprocessing.dao

import com.hanxuanliang.logprocessing.domain.CourseClickCount
import com.hanxuanliang.logprocessing.util.HBaseUtils
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.util.Bytes
import scala.collection.mutable.ListBuffer

object CourseClickCountDao {
  val TABLENAME = "logcourse_clickcount"
  val CF = "info"
  val QUALIFER = "click_count"

  def save(list: ListBuffer[CourseClickCount]): Unit = {
    val table = HBaseUtils.getInstance().getTable(TABLENAME)
    for(ele <- list) {
      table.incrementColumnValue(
        Bytes.toBytes(ele.day_course),
        Bytes.toBytes(CF), Bytes.toBytes(QUALIFER), ele.click_count)
    }
  }

  def count(day_course: String): Long = {
    val table = HBaseUtils.getInstance().getTable(TABLENAME)

    val get = new Get(Bytes.toBytes(day_course))
    val value = table.get(get).getValue(CF.getBytes, QUALIFER.getBytes)

    if (value == null) 0L
    else Bytes.toLong(value)
  }

  def main(args: Array[String]): Unit = {
    val list = new ListBuffer[CourseClickCount]
    list.append(CourseClickCount("20190703_8", 8))
    list.append(CourseClickCount("20190703_9", 9))
    list.append(CourseClickCount("20190703_1", 100))

    save(list)
    print(count("20190703_8") + " : " +
      count("20190703_9") + " : "
      + count("20190703_1"))
  }
}
