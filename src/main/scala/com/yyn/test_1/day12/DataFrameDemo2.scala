package com.yyn.test_1.day12

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: DataFrameDemo2
  */
object DataFrameDemo2 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
    val sc = new SparkContext(conf)
    val spark = SparkSession.builder().config(conf).getOrCreate()

    val peopleRdd = sc.textFile("dir/people.txt")
      .map(_.split(","))
    val tupRdd: RDD[People] = peopleRdd.map(x => People(x(0),x(1).toInt))

    import spark.implicits._
    val df: DataFrame = tupRdd.toDF()   //反射生成Schema信息

    df.show()

    val rdd: RDD[Row] = df.rdd

    println(rdd.collect.toBuffer)

    sc.stop()
    spark.stop()

  }

}

case class People(name:String,age: Int){

}
