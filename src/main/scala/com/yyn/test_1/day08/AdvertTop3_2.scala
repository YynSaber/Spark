package com.yyn.test_1.day08

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.joda.time.DateTime

/**
  * @title: AdvertTop3_2
  * @projectName test_1
  * @author YongningY
  * @date 2019/7/31 11:37
  */
object AdvertTop3_2 {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("advert").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    //读取数据并切分
    val logs: RDD[String] = sc.textFile("H://advert/advert/Advert.log")
    val logArr: RDD[Array[String]] = logs.map(_.split("\t"))

    //提取要分析需求 需要的数据
    // 将数据整合成元组，便于聚合，key=省份+小时+广告id
    val proAndHourAndAdId: RDD[(String, Int)] =
      logArr.map(x => (x(1) + "_" + getHour(x(0).toLong) + "_" + x(4),1))


    // 聚合生成每个省份的每个小时的每个广告的点击量
    val aggred: RDD[(String, Int)] = proAndHourAndAdId.reduceByKey(_+_)

    // 重新整合数据，便于接下来的分组排序
    val proAndHourTup = aggred.map(tup => {
      val splited = tup._1.split("_")
      val pro = splited(0)
      val hour = splited(1)
      val adId = splited(2)
      ((pro, hour), (adId, tup._2))
    })


    // 用省份和小时进行分组
    val grouped: RDD[((String, String), Iterable[(String, Int)])] = proAndHourTup.groupByKey()

    // 开始组内排序
    val res: RDD[((String, String), List[(String, Int)])] = grouped.mapValues(x => x.toList.sortWith(_._2 > _._2).take(3))

    println(res.collect.toBuffer)

    sc.stop()
  }

  /**
    * 获取时间戳的小时的方法
    * @param time_long
    * @return
    */
  def getHour(time_long: Long) = {
    val datetime: DateTime = new DateTime(time_long)
    datetime.getHourOfDay.toString
  }
}
