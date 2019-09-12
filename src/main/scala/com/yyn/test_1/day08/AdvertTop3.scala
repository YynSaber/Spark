package com.yyn.test_1.day08

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: Advert
  * @author YongningY
  * @date 2019/7/31 10:50
  *
  * 数据字段: 时间戳  省份  城市  userId  adId
  * 需求  求每个省份的点击广告数的top3
  */
object AdvertTop3 {

  def main(args: Array[String]): Unit = {
    //初始化环境
    val conf: SparkConf = new SparkConf().setAppName("advert").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    //读取数据并切分
    val logs: RDD[String] = sc.textFile("H://advert/advert/Advert.log")
    val logArr: RDD[Array[String]] = logs.map(_.split("\t"))

    //提取要分析需求 需要的数据
    val proviceAndAdId: RDD[(String, Int)] =
      logArr.map(x=>(x(1) + "_" + x(4),1))



    //将省份对应的多个广告进行点击量统计
    val aggrPro: RDD[(String, Int)] = proviceAndAdId.reduceByKey(_+_)

//    println(aggrPro.collect.toBuffer)

    //
    val aggProAdTup: RDD[(String, String, Int)] = aggrPro.map(tup => {
      val splited = tup._1.split("_")
      val province = splited(0)
      val adId = splited(1)
      (province, adId, tup._2)
    })
    println(aggProAdTup.collect.toBuffer)

    //按照省份进行分组
    val grouped: RDD[(String, Iterable[(String, String, Int)])] =
      aggProAdTup.groupBy(_._1)

    val res: RDD[(String, List[(String, String, Int)])] =
      grouped.mapValues(x=>x.toList.sortWith(_._3>_._3).take(3))

//    val res: Array[(String, Int)] = proviceAndAdId.reduceByKey(_+_).takeOrdered(3)
    println(res.collect.toBuffer)

//    res.saveAsTextFile("H://f")

    sc.stop()
  }

}
