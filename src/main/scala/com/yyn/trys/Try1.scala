package com.yyn.trys

import java.text.SimpleDateFormat
import java.util.{Calendar, Properties}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

//    logs.rdd.map(x => {
//      val video_type = x.get(0).toString
//      val video_style = x.get(1).toString
//      val video_duration = x.get(2).toString.toInt
//      val video_duration_play = x.get(3).toString.toInt
//      val ad_type = x.get(4).toString
//      val ad_duration = x.get(5).toString.toInt
//      val ad_duration_play = x.get(6).toString.toInt
//      val video_tag = x.get(7).toString
//      val video_sections = x.get(8).toString
//      val video_produced_area = x.get(9).toString
//      val video_produced_time = x.get(10).toString
//      val video_director = x.get(11).toString
//      val video_actor = x.get(12).toString
//      val video_name = x.get(13).toString
//      val user = x.get(14).toString
//      val ct = x.get(15).toString
//      (video_type, video_style, video_duration,video_duration_play,
//        ad_type,ad_duration,ad_duration_play,video_tag,video_sections,
//        video_produced_area,video_produced_time,video_director,video_actor,
//        video_name,user,ct)
//    })
object Try1 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setAppName(this.getClass.getName)
      .setMaster("local[2]")
    val sc = new SparkContext(conf)

    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    import org.apache.spark.sql.functions._

    val logs: DataFrame = spark.read.parquet("dir/1563336707000aj235jk6")

//    val all: RDD[(String, (String, String, String, String,String))] = logs.rdd.map(x => {
//      val video_type = x.get(0).toString
//      val video_style = x.get(1).toString
//      val video_produced_area = x.get(9).toString
//      val video_produced_time = x.get(10).toString
//      val user = x.get(14).toString
//      val ct = x.get(15).toString
//      (video_type, (video_style, video_produced_area, video_produced_time, user,getDate(ct)))
//    })


    val tup1 = logs.rdd.map(x => {
      val video_type = x.get(0).toString
//            val video_style = x.get(1).toString
//      val video_duration = x.get(2).toString.toInt
      val video_duration_play = x.get(5).toString.toInt
      val ad_duration_play = x.get(6).toString.toInt

      val video_produced_area = x.get(9).toString
      val video_produced_time = x.get(10).toString

      val user = x.get(14).toString
      val ct = x.get(15).toString
      ((getDate(ct), video_type, video_produced_area, video_produced_time), video_duration_play, ad_duration_play, user)
    })

    val group1: RDD[((String, String, String, String), Iterable[((String, String, String, String), Int, Int, String)])] = tup1.groupBy(_._1)

    val aggr: RDD[((String, String, String, String), (Int, Int, Int))] = group1.mapValues(x => (
      x.map(x => x._2).reduce((x, y) => x + y),
      x.map(x => x._3).reduce((x, y) => x + y),
      x.map(x => x._4).toList.distinct.size
    ))

    val res1 = aggr.map(x=> (x._1._1,x._1._2,x._1._3,x._1._4,x._2._1,x._2._2,x._2._3))

    println(res1.collect.toBuffer)

//    res1.foreach(println)

    import spark.implicits._
    val tmp1: DataFrame = res1.toDF("date", "type", "area", "product_time", "video_time", "ad_time", "uv")

    tmp1.createOrReplaceTempView("t")
    spark.sql("select * from t").show()

    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "123456")
    val url = "jdbc:mysql://localhost:3306/test"
    tmp1.write.mode(SaveMode.Append).jdbc(url,"t1",prop)

  }

  def getDate(longDate:String):String ={
    val sdf=new SimpleDateFormat("yyyy-MM-dd")
    val calendar= Calendar.getInstance()
    calendar.setTimeInMillis(longDate.toLong)
    val date2=sdf.format(calendar.getTime)
    date2
  }

}

