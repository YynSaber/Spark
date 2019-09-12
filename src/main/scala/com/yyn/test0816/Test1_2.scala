package com.yyn.test0816

import java.text.SimpleDateFormat
import java.util.{Calendar, Properties}

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author 杨永宁
  * @date 2019/8/16 14:15
  */
object Test1_2 {


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setAppName(this.getClass.getName)
      .setMaster("local[2]")
    val sc = new SparkContext(conf)

    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val logs: DataFrame = spark.read.parquet("dir/1563336707000aj235jk6")

    val tup1 = logs.rdd.map(x => {
      val video_type = x.get(0).toString
      val video_style = x.get(1).toString
      //      val video_duration = x.get(2).toString.toInt
      val video_duration_play = x.get(5).toString.toInt
      val ad_duration_play = x.get(6).toString.toInt

      val video_produced_area = x.get(9).toString
      val video_produced_time = x.get(10).toString

      val user = x.get(14).toString
      val ct = x.get(15).toString
      ((getDate(ct), video_type,video_style, video_produced_area, video_produced_time), video_duration_play, ad_duration_play, user)
    })

    val group1: RDD[((String, String, String, String, String), Iterable[((String, String, String, String, String), Int, Int, String)])] = tup1.groupBy(_._1)

    val aggr = group1.mapValues(x => {
      val v1 = x.map(x => x._2).reduce((x,y)=> x+y)
      val v2 = x.map(x => x._3).reduce((x,y) => x+y)
      val v3 = x.map(x => x._4).toList.distinct.size
      (v1,v2,v3)
    })

    val res2 = aggr.map(x=> (x._1._1,x._1._2,x._1._3,x._1._4,x._1._5,x._2._1,x._2._2,x._2._3))

    res2.foreach(println)

    import spark.implicits._
    val tmp2: DataFrame = res2.toDF("date", "type","style", "area", "product_time", "video_time", "ad_time", "uv")

    tmp2.createOrReplaceTempView("tt")
    spark.sql("select * from tt").show()

    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "123456")
    val url = "jdbc:mysql://localhost:3306/test?useSSL=false"
    tmp2.write.mode(SaveMode.Append).jdbc(url,"t2",prop)



  }

  def getDate(longDate:String):String ={
    val sdf=new SimpleDateFormat("yyyy-MM-dd")
    val calendar= Calendar.getInstance()
    calendar.setTimeInMillis(longDate.toLong)
    val date2=sdf.format(calendar.getTime)
    date2
  }


}
