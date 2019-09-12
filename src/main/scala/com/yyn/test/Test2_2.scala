package com.yyn.test

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * @title: Test2_2
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/13 14:44
  */
object Test2_2 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkWordCount").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val file: RDD[String] = sc.textFile("dir/idtimeurl.txt")
    val tups: RDD[(String, String, String)] = file.map(x => {
      val split = x.split(" ")
      (split(0), split(1), split(2))
    })
    val grouped: RDD[(String, Iterable[(String, String, String)])] = tups.groupBy(_._1)

    val res: RDD[(String, List[(String, String, String)])] = grouped.mapValues(_.toList.sortBy(_._2))
//        val res: Array[(String, List[(String, String, String)])] = grouped.mapValues(_.toList.sortBy(_._2)).take(3)

    res.saveAsTextFile("h://out11")
    println(res.collect.toBuffer)

  }

}
