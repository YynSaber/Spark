package com.yyn.test_1.day12

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * @title: DSLDemo
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/6 14:56
  */
object DSLDemo {

  def main(args: Array[String]): Unit = {

    //创建SparkConf()并设置App名称
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local")
    //创建SparkContext对象
    val sc = new SparkContext(conf)
    //创建SparkSession对象
    val spark = SparkSession.builder().config(conf).getOrCreate()

    val df: DataFrame = spark.read.json("dir/people.json")

    //DSL语言风格操作
    df.show()
    df.select("name").show()
    //打印Schema信息
    df.printSchema()
    spark.stop()


  }

}
