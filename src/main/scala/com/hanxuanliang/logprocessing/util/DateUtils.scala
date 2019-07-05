package com.hanxuanliang.logprocessing.util

import java.util.Date

import org.apache.commons.lang3.time.FastDateFormat

object DateUtils {
  val Y4M2D2H2M2S2_FORMAT: FastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
  val TARGET_FORMAT: FastDateFormat = FastDateFormat.getInstance("yyyyMMddHHmmss")

  def getTime(time: String): Long = {
    Y4M2D2H2M2S2_FORMAT.parse(time).getTime
  }

  def parseToMinute(time: String): String = {
    TARGET_FORMAT.format(new Date(getTime(time)))
  }

  def main(args: Array[String]): Unit = {
    print(parseToMinute("2019-07-03 13:14:02"))
  }

}
