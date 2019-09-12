package com.yyn.trys

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object exercise {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setAppName(this.getClass.getName)
      .setMaster("local[*]")

    val spark = SparkSession.builder()
      .config(conf)
      .enableHiveSupport()
      .getOrCreate()

    val df: DataFrame = spark.read.parquet("dir/1563336707000aj235jk6")

    val tup: RDD[(Long, String, String, String, String, String, String)] = df.rdd.map(x => {
      val video_type = x(0).toString
      val video_style = x(1).toString
      val video_duration = x(2).toString
      val ad_type = x(3).toString
      val ad_duration = x(4).toString
      val video_duration_play = x(5).toString
      val ad_duration_play = x(6).toString
      val video_tag = x(7).toString
      val video_sections = x(8).toString
      val video_produced_area = x(9).toString
      val video_produced_time = x(10).toString
      val video_director = x(11).toString
      val video_actor = x(12).toString
      val video_name = x(13).toString
      val user = x(14).toString
      val ct = x(15).toString.toLong

      (ct, video_type, video_produced_area,
        video_produced_time, video_duration_play,
        ad_duration_play, user)
    })

//  日期 视频分类 出品地区 出品时间 视频播放总时长 广告播放总时长 用户UV
    val res1: List[(String, String, String, String, Int, Int, Int)] = tup.map(x => ((tranTime(x._1), x._2, x._3, x._4), x._5.toInt, x._6.toInt, x._7))
      .groupBy(x => x._1)
      .map(x => (
        x._1._1, x._1._2, x._1._3, x._1._4,
        x._2.map(x => x._2).reduce((x, y) => x + y),
        x._2.map(x => x._3).reduce((x, y) => x + y),
        x._2.size)).collect.toList




    for(i <- 0 until res1.length){
      println(res1(i))
    }

//    tup.map(x => ((tranTime(x._1), x._2, x._3, x._4), x._5.toInt, x._6.toInt, x._7))
//      .groupBy(x => x._1).foreach(x => println(x))

  }

  def tranTime(ct:Long): String ={
    val fm = new SimpleDateFormat("yyyy-MM-dd")
    val tim = fm.format(new Date(ct))
    tim
  }
}
