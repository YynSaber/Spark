package com.yyn.test_1.day12

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * @title: SparkSqlDemo
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/6 11:18
  */
object SparkSqlDemo {

  def main(args: Array[String]): Unit = {
    //第一种创建方式
    val spark1: SparkSession = SparkSession.builder()
      .appName("sparksqldemo")
      .master("local[2]")
      .getOrCreate
    spark1

    //第二种创建方式
    val conf = new SparkConf().setAppName("sparksqldemo").setMaster("local[2]")
    val spark2 = SparkSession.builder()
      .config(conf)
      .getOrCreate()
    spark2

    //第三种创建方式
    val spark3: SparkSession = SparkSession.builder()
      .appName("sparksqldemo")
      .master("local[2]")
      .enableHiveSupport()   //启用hive支持
      .getOrCreate()
    spark3

    spark1.stop()
    spark2.stop()
    spark3.stop()


  }

}
