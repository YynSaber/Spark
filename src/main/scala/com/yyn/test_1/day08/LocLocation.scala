package com.yyn.test_1.day08

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: LocLocation
  * @projectName test_1
  * @author YongningY
  * @date 2019/7/31 20:17
  */
object LocLocation {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("location").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    val logs: RDD[String] = sc.textFile("H://idea/03.SparkCore/dataSource/lacduration/log")

    val userInfo: RDD[((String, String), Long)] = logs.map(line => {
      val f = line.split(",")
      val phone = f(0)
      val time = f(1).toLong
      val lac = f(2)
      val eventType = f(3)
      //判断时间是否是建立连接时间 如果是 前面加"-" 如果不是 直接返回 方便后边计算
      val time_long = if (eventType.equals("1")) -time else time
      ((phone, lac), time_long)
    })


    //统计每个用户在所有基站停留的总时长
    val aggred: RDD[((String, String), Long)] = userInfo.reduceByKey(_+_)

    aggred.foreach(println)

    val lacPhoneTime: RDD[(String, (String, Long))] = aggred.map(tup => {

      val phone = tup._1._1
      val lac = tup._1._2
      val time = tup._2

      (lac, (phone, time))

    })

    val lacInfo = sc.textFile("H://idea/03.SparkCore/dataSource/lacduration/lac_info.txt")
      .map(line => {
        val field = line.split(",")
        val lac = field(0)
        val lang = field(1)
        val lat = field(2)

        (lac, (lang, lac))

      })

    //将聚合后的用户信息 基站信息join
    val joined: RDD[(String, ((String, Long), (String, String)))] =
      lacPhoneTime join lacInfo

    //整合数据 方便按手机号排序
    val phoneTimeLanglat = joined.map(tup => {
      val phone = tup._2._1._1
      val time = tup._2._1._2
      val langLat = tup._2._2

      (phone, (time, langLat))
    })
    val grouped: RDD[(String, Iterable[(Long, (String, String))])] =
      phoneTimeLanglat.groupByKey()

    val res: Array[(String, List[(Long, (String, String))])] = grouped.mapValues(_.toList.sortWith(_._1>_._1)).take(3)

    res.foreach(println)

    sc.stop()
  }
}
