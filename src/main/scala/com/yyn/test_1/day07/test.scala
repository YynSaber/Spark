package com.yyn.test_1.day07

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: test
  * @projectName test_1
  * @author YongningY
  * @date 2019/7/30 16:59
  */
object test {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Test").setMaster("local")
    val sc = new SparkContext(conf)

    val rdd: RDD[Int] = sc.parallelize(List(5,6,4,7,3,8,2,9,10))

    val rdd2: RDD[String] = sc.parallelize(Array("a b c","b c d"))

  }

}
