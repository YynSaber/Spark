package com.yyn.trys

import java.text.SimpleDateFormat
import java.util.{Calendar, Properties}

import org.apache.spark.sql.{DataFrame, Row, SparkSession, types}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.types._

object dw {

  def main(args: Array[String]): Unit = {
    val conf =new SparkConf().setAppName("VideoDemo").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val spark=SparkSession.builder().config(conf).getOrCreate()
    val df = spark.read.parquet("dir/1563336707000aj235jk6")
    df.show(10)
    df.createOrReplaceTempView("table")

    val dp=spark.sql("select ct,video_type,video_produced_area,video_produced_time,video_duration,ad_duration_play,user from table")
    val dq=dp.rdd.map(x => {
      val v1 =getDate(x(0).toString)
      val v2=x(1).toString
      val v3=x(2).toString
      val v4=x(3).toString
      val v5=x(4).toString
      val v6=x(5).toString
      val v7=x(6).toString

      Row(v1,v2,v3,v4,v5,v6,v7)
    })


    val sceme=new StructType(
      Array(
        StructField("ct",StringType,true),
        StructField("video_type",StringType,true),
        StructField("video_produced_area",StringType,true),
        StructField("video_produced_time",StringType,true),
        StructField("video_duration",StringType,true),
        StructField("ad_duration_play",StringType,true),
        StructField("user",StringType)
      )
    )

    //    val dw: DataFrame = spark.createDataFrame(dq,sceme)
    //    val prop = new Properties()
    //    prop.put("user","root")
    //    prop.put("password","123456")
    //    val url="jdbc:mysql://192.168.255.102:3306/video"
    //    dw.write.jdbc(url,"tableA",prop)
    //    println("写入完成")

    val dm=spark.sql("select ct,video_type,video_style,video_produced_area,video_produced_time,video_duration,ad_duration_play,user from table")
    val dn=dm.rdd.map(x => {
      val v1 =getDate(x(0).toString)
      val v2=x(1).toString
      val v3=x(2).toString
      val v4=x(3).toString
      val v5=x(4).toString
      val v6=x(5).toString
      val v7=x(6).toString
      val v8=x(7).toString
      Row(v1,v2,v3,v4,v5,v6,v7,v8)
    })


    val scheme=new StructType(
      Array(
        StructField("ct",StringType,true),
        StructField("video_type",StringType,true),
        StructField("video_style",StringType,true),
        StructField("video_produced_area",StringType,true),
        StructField("video_produced_time",StringType,true),
        StructField("video_duration",StringType,true),
        StructField("ad_duration_play",StringType,true),
        StructField("user",StringType)
      )
    )
    val sq: DataFrame = spark.createDataFrame(dn,scheme)
    val prop1 = new Properties()
    prop1.put("user","root")
    prop1.put("password","123456")
    val url1="jdbc:mysql://192.168.255.102:3306/video"
    sq.write.jdbc(url1,"tableB",prop1)
    println("写入完成")




  }

  def getDate(longDate:String):String ={
    val sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val calendar= Calendar.getInstance()
    calendar.setTimeInMillis(longDate.toLong)
    val date2=sdf.format(calendar.getTime)
    date2
  }
}
