package com.hanxuanliang

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object TransformApp {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("TransformApp")

    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val blacks = List("zs", "ls")
    val blacksRDD = ssc.sparkContext.parallelize(blacks).map(x => (x, true))

    val lines = ssc.socketTextStream("localhost", 6789)

    /**
      * test case:
      *   20190702 zs
      *   20190702 ls
      *   20190702 ww
      *   20190702 zf
      */
    val result = lines.map(x => (x.split(",")(1), x)).transform(rdd => {
      rdd.leftOuterJoin(blacksRDD)
        .filter(x => !x._2._2.getOrElse(false))
        .map(x => x._2._1)
    })

    result.print()

    ssc.start()
    ssc.awaitTermination()

  }
}
