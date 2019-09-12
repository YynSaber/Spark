package com.yyn.test

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
  * @title: Test1_1
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/8 16:09
  */
object Test1_1 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    //2.创建SparkContext 提交SparkApp的入口
    val sc = new SparkContext(conf)

    val datas: RDD[String] = sc.textFile("dir/test.txt")


    val field: RDD[Int] = datas.map(x => {
      val splited = x.split(" ")
      splited.length
    })

    val res: Array[Int] = field.sortBy(x => x,false).take(1)

    println(res.toBuffer)


    //    val res1: Array[(List[String], Int)] =
    //      datas.map(x => x.split(" "))
    //      .map(x => (x.toList, x.length))
    //      .sortBy(x => x._2, false)
    //      .take(1)

//    println(res1.toList)


  }

}
