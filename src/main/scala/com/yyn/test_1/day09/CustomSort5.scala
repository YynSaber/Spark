package com.yyn.test_1.day09

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @title: CustomSort5
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/2 11:29
  */
object CustomSort5 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
    val sc = new SparkContext(conf)

    val userInfo: RDD[String] = sc.parallelize(Array("mi 25 85","bing 30 85","yuan 18 90"))
    val personRdd = userInfo.map(x => {
      val arr = x.split(" ")
      val name = arr(0)
      val age = arr(1).toInt
      val fv = arr(2).toInt

      (name, age, fv)

    })

    //排序
    val sorted: RDD[(String, Int, Int)] = personRdd.sortBy(x=>(-x._3,x._2))

    println(sorted.collect.toBuffer)
  }
}
