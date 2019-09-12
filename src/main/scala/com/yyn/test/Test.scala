package com.yyn.test

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: Test
  * @projectName test_1
  * @author YongningY
  * @date 2019/7/31 21:54
  */
object Test {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)
    val logs: RDD[String] = sc.textFile("H://idea/access.txt")

    val lines: RDD[Array[String]] = logs.map(_.split("/"))
    //生成数据的格式是（科目_类别，网址）
    val tuple: RDD[(String, String)] = lines.map(x=>(x(2)+"/"+x(4),x(0).split("\t")(1)+"//"+x(2)+"/"+x(3)+"/"+x(4)))
    //生成数据的数据结构是（（科目_类别，网址），1）
    val res_1: RDD[((String, String), Int)] = tuple.map(x=>(x,1))
    //生成数据的数据结构是（（科目_类别，网址），数量）
    val res_2: RDD[((String, String), Int)] = res_1.reduceByKey(_+_)
    //生成的数据的数据结构为（科目，[(科目_类别，网址),数量]）
    val res_3: RDD[(String, Iterable[((String, String), Int)])] = res_2.groupBy(_._1._1.split("/")(0))
    //进行排序并取前三
    val res_4: RDD[(String, List[((String, String), Int)])] = res_3.mapValues(_.toList.sortWith(_._2>_._2).take(3))
    //进行数据格式的整理（科目，[(科目类别，网址)]）
    val res_5: RDD[(String, List[((String, String), String)])] = res_4.map(x => {
      val sub = x._1 //获取科目
      val list = x._2 //获取集合
      val list_res = list.map(y => {
        val cla = y._1._1.split("/")(1)
        val dir = y._1._2
        val num = y._2
        (("科目类别为：" + cla, "网址为：" + dir), "浏览量为：" + num)
      })
      ("科目为：" + sub, list_res)
    })
    println(res_5.collect.toList.toBuffer)


  }

}
