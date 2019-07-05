package com.hanxuanliang.logprocessing.spark

import com.hanxuanliang.logprocessing.dao.{CourseClickCountDao, CourseSearchClickCountDao}
import com.hanxuanliang.logprocessing.domain.{ClickLog, CourseClickCount, CourseSearchClickCount}
import com.hanxuanliang.logprocessing.util.DateUtils
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

object StreamingApp {
  def main(args: Array[String]): Unit = {
    if (args.length != 4) {
      System.err.println("Usage: StreamingApp <zkQuorm> <group> <topics> <numThreads>")
      System.exit(1)
    }

    val Array(zkQuorm, groupId, topics, numThreads) = args

    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("StreamingApp")

    val ssc = new StreamingContext(sparkConf, Seconds(60))

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val messages = KafkaUtils.createStream(ssc, zkQuorm, groupId, topicMap)

    // 测试数据收集的情况
//    messages.map(_._2).count().print()
    val logs = messages.map(_._2)
    val cleanedData = logs.map(line => {
      val infos = line.split("\t")
      val url = infos(2).split(" ")(1)
      var courseId = 0
      if (url.startsWith("/class")) {
        val courseIdHTML = url.split("/")(2)
        courseId = courseIdHTML.substring(0, courseIdHTML.lastIndexOf(".")).toInt
      }

      ClickLog(
        infos(0), DateUtils.parseToMinute(infos(1)),
        courseId, infos(3).toInt, infos(4))
    }).filter(clickLog => {clickLog.courseId != 0})

//    cleanedData.print()
    cleanedData.map(x => {
      (x.time.substring(0, 8) + "_" + x.courseId, 1)
    }).reduceByKey(_+_).foreachRDD(rdd => {
      rdd.foreachPartition(partitionRecords => {
        val list = new ListBuffer[CourseClickCount]
        partitionRecords.foreach(pair => {
          list.append(CourseClickCount(pair._1, pair._2))
        })
        CourseClickCountDao.save(list)
      })
    })

    cleanedData.map(x => {
      val referer = x.referer.replaceAll("//", "/")
      val splits = referer.split("/")
      var host = ""
      if (splits.length > 2) {
        host = splits(1)
      }
      (host, x.courseId, x.time)
    }).filter(_._1 != "").map(x => {
      (x._3.substring(0, 8) + "_" + x._1 + "_" + x._2, 1)
    }).reduceByKey(_+_).foreachRDD(rdd => {
      rdd.foreachPartition(partitionRecords => {
        val list = new ListBuffer[CourseSearchClickCount]
        partitionRecords.foreach(pair => {
          list.append(CourseSearchClickCount(pair._1, pair._2))
        })
        CourseSearchClickCountDao.save(list)
      })
    })

    ssc.start()
    ssc.awaitTermination()
  }
}
