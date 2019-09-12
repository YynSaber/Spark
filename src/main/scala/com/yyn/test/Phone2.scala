package com.yyn.test

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
  * @title: Phone2
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/6 23:48
  */
object Phone2 {

  def main(args: Array[String]): Unit = {
    //创建SparkConf()并设置App名称
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local")
    //创建SparkContext对象
    val sc = new SparkContext(conf)
    //创建SparkSession对象
    val spark = SparkSession.builder().config(conf).getOrCreate()

    //获取数据并切分 生成Row类型数据
    val peopleRowRdd: RDD[Row] = sc.textFile("H://idea/JsonTest02.json").map(x => {
      val line = x.split(",")
      val phone = line(1).split(":")(1)
      val money = line(2).split(":")(1).split("\"")(1).toInt
      val privince = line(6).split(":")(1)
      val terminal = line(9).split(":")(1)
      val status = line(10).split(":")(1).split("\"")(1).toInt
      Row(phone,money,privince,terminal,status)
    })


    println(peopleRowRdd.collect.toBuffer)

    //通过StructType生成Schema
    val schema: StructType = StructType(
      Array(
        StructField("phone", StringType, true),
        StructField("money", IntegerType, true),
        StructField("province", StringType, true),
        StructField("terminal", StringType, true),
        StructField("status", IntegerType, true)
      )
    )
//    println(schema)
//    转换生成DataFrame
    val df: DataFrame = {
      spark.createDataFrame(peopleRowRdd, schema)
    }



    df.show()
  }

}
