package com.yyn.test_1.day09

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @title: CustomSort4
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/2 11:17
  */

//Ordering自带的on方法
object CustomSort4 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
    val sc = new SparkContext(conf)

    val userInfo: RDD[String] = sc.parallelize(Array("mi 25 85","bing 30 85","yuan 18 90"))
    val personRdd: RDD[(String, Int, Int)] = userInfo.map(x => {
      val arr = x.split(" ")
      val name = arr(0)
      val age = arr(1).toInt
      val fv = arr(2).toInt
      (name, age, fv)
    })
    /**
      * (Int,Int) 指定函数返回的类型
      * (String, Int, Int):On方法输入参数的类型
      */
    //指定隐式值
    implicit val ord: Ordering[(String, Int, Int)] = Ordering[(Int,Int)].on[(String,Int,Int)](x=>(-x._3,x._2))

    //排序
    val sorted: RDD[(String, Int, Int)] = personRdd.sortBy(x=>x)
    println(sorted.collect.toBuffer)
  }
}
