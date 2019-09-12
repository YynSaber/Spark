package com.yyn.test

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @title: Tr
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/1 8:21
  */
object Tr {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("test1").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)
    val logs: RDD[String] = sc.textFile("H://idea/access.txt")

//    val fields: RDD[Array[String]] = logs.map(_.split("/"))

//    val ips: RDD[Array[String]] = logs.map(_.split("/t"))

    val tups = logs.map(lines => {
      val field: Array[String] = lines.split("/")
      val ip: Array[String] = lines.split("/t")
      //      val name = field(2)
      //    val ips = field(0).split("\t")(1) + "//" + field(2) + "/" + field(3) + "/" + field(4)
      //      val ips = ip(1)
      //      (name, (name, ips))
      //    (field(2) + "_" + ips)

      (field(2) + "_" + ip(1))

    })

//    println(tups)

    val nameIps = tups.map(x => (x, 1))

    val aggred=nameIps.reduceByKey(_+_)

    //    println(aggred.collect.toBuffer)

    val reAggred = aggred.map(tup => {
      val splited = tup._1.split("_")
      val name = splited(0)
      val ip = splited(1)
      (name, ip, tup._2)
    })


    val grouped: RDD[(String, Iterable[(String, String, Int)])] = reAggred.groupBy(_._1)

    val res: RDD[(String, List[(String, String, Int)])] = grouped.mapValues(x=>x.toList.sortWith(_._3>_._3).take(3))

    println(res.collect.toBuffer)

  }

}
