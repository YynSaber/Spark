package com.yyn.trys

import java.text.SimpleDateFormat
import java.util.Calendar

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: Trys
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/15 11:19
  */
object Trys {

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

    val video: DataFrame = spark.read.parquet("dir/1563336707000aj235jk6")

    video.createOrReplaceTempView("t_v")
;
    spark.sql("select from_unixtime(ct/1000,'yyyy-MM-dd') ct,video_type,video_produced_area,video_produced_time," +
      "sum(video_duration_play),sum(ad_duration_play),count(distinct user)" +
      "from t_v group by from_unixtime(ct/1000,'yyyy-MM-dd'),video_type,video_produced_area,video_produced_time" ).show()

    val res = video
//      .select("video_type","video_produced_time")
      .groupBy("video_type")
      .agg(sum("video_duration_play"),sum("ad_duration_play"))
//      .sort("getDate(ct)")


//    res.show()

//    video.show()
    spark.stop()
    sc.stop()


  }
  def getDate(longDate:String):String ={
    val sdf=new SimpleDateFormat("yyyy-MM-dd")
    val calendar= Calendar.getInstance()
    calendar.setTimeInMillis(longDate.toLong)
    val date2=sdf.format(calendar.getTime)
    date2
  }
}
