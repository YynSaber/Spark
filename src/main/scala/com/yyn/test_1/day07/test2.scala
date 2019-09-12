package com.yyn.test_1.day07

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: test2
  * @projectName test_1
  * @author YongningY
  * @date 2019/7/31 9:15
  */
object test2 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Test").setMaster("local")
    val sc = new SparkContext(conf)

    val lines: RDD[String] = sc.textFile("h://access.log")
    val ip: RDD[String] = lines.map(_.split("-")(0))

    val eve: RDD[(String, Int)] = ip.map(x=>(x,1))

    val grou: RDD[(String, Iterable[(String, Int)])] = eve.groupBy(_._1)

    val value = grou.mapValues(_.size)

    println(value.collect.toBuffer)

    val pv=ip.count()
    val uv=ip.distinct().count()

    println(pv +" " + uv)
//    println(str)

//    words.map((_, 1))


  }

}
