package com.yyn.test

import java.net.URL

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
  * @title: su
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/1 11:30
  */
object su {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
    val sc = new SparkContext(conf)
    // 1.对数据进行切分
    val tuples: RDD[(String, Int)] =
      sc.textFile("H://idea/access.txt").map(line => {
        val fields: Array[String] = line.split("\t")
        //取出url
        val url = fields(1)
        (url, 1)
      })
    //将相同url进行聚合,得到了各个学科的访问量
    val sumed: RDD[(String, Int)] = tuples.reduceByKey(_+_)

    //从url中获取学科的字段 数据组成式 学科, url 统计数量
    val subjectAndUC: RDD[(String, String, Int)] = sumed.map(tup => {
      val url = tup._1 //用户url
      val count = tup._2 // 统计的访问数量
      val subject = new URL(url).getHost //学科
      (subject, url, count)
    })
    //按照学科信息进行分组
    val grouped: RDD[(String, Iterable[(String, String, Int)])] = subjectAndUC.groupBy(_._1)
    //对分组数据进行组内排序
    val sorted: RDD[(String, List[(String, String, Int)])] =
      grouped.mapValues(_.toList.sortBy(_._3).reverse)
    //取top3
    val res: RDD[(String, List[(String, String, Int)])] = sorted.mapValues(_.take(3))
    println(res.collect.toList)
    sc.stop()
  }
}

