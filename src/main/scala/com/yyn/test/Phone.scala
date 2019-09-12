package com.yyn.test

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: Phone
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/6 8:23
  */
object Phone {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("this.getClass.getName").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val files: RDD[String] = sc.textFile("H://idea/JsonTest02.json")

    sumMony(files)



  }

  def sumMony(files: RDD[String])={

    val lines = files.map(x => {
      val line = x.split(",")
      val phone = line(1).split(":")(1)
      val money = line(2).split(":")(1).split("\"")(1).toInt
      val status = line(10).split(":")(1).split("\"")(1).toString
      ((phone,status),money)
    })

    val filtered: RDD[((String, String), Int)] = lines.filter(_._1._2.equals("1"))

    val re: RDD[(String, Int)] = filtered.map(x => {
      val phone = x._1._1
      val money = x._2
      (phone, money)
    })

    val reaggr: RDD[(String, Int)] = re.reduceByKey(_+_)

    val sorted: RDD[(String, Int)] = reaggr.sortBy(_._2,false)

    println(sorted.collect.toBuffer)



  }

}
