package com.yyn.test

import com.yyn.test_1.day12.People
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SaveMode, SparkSession}

/**
  * @title: Test2
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/13 14:18
  */
object Test2 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val file: RDD[Array[String]] = sc.textFile("dir/idtimeurl.txt").map(_.split(" "))

//    val tupps: RDD[Msg] = file.map(x => Msg(x(0),x(1),x(2)))
//
//    import spark.implicits._
//    val ds: Dataset[Msg] = tupps.toDS()
//
//    ds.createOrReplaceTempView("t_table")

    val tupps = file.map(x => (x(0),x(1),x(2)))

    import spark.implicits._
    val ds = tupps.toDF("id","time","url")

    ds.createOrReplaceTempView("t_table")

    val df: DataFrame = spark.sql("select id,time,url from (select row_number() over(partition by id order by time) rk,id,time,url from t_table)t where rk <= 3 ")

    df.show()

    df.write.mode(SaveMode.Append).json("hdfs:/hadoop01:8020/out-2019-08-13-3")
    spark.stop()
  }

}
case class Msg(id:String,time:String,url:String)
