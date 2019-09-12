package com.yyn.test_1.day12

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 通过反射获取Scheam(样例类模式)
  */
object RDD2DataSet {
  def main(args: Array[String]): Unit = {


    //创建SparkConf()并设置App名称
    val conf = new SparkConf().setAppName("SparkSQLDemo").setMaster("local")
    //创建SparkContext对象
    val sc = new SparkContext(conf)
    //创建SparkSession对象
    val spark = SparkSession.builder().config(conf).getOrCreate()

    val peopleRdd1: RDD[String] = sc.textFile("dir/people.txt")
    println(peopleRdd1.collect.toBuffer)

    val peopleRdd: RDD[Array[String]] = sc.textFile("dir/people.txt")
      .map(_.split(","))
//    peopleRdd

    val tupRdd: RDD[People] = peopleRdd.map(x => People(x(0),x(1).toInt))

    import spark.implicits._
    //
    val ds: Dataset[People] = tupRdd.toDS()

    val tab: Unit = ds.createOrReplaceTempView("t_people")
    val res: DataFrame = spark.sql("select * from t_people where age >20")
    res.show()

    ds.show()

    sc.stop()
    spark.stop()

  }
}
