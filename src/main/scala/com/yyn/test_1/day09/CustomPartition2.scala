package com.yyn.test_1.day09

import java.net.URL

import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/**
  * @title: CustomPartition2
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/2 14:29
  */
object CustomPartition2 {

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
    val sumed: RDD[(String, Int)] = tuples.reduceByKey(_ + _).cache()

    //从url中获取学科的字段 数据组成式 学科, url 统计数量
    val subjectAndUC: RDD[(String, (String, Int))] = sumed.map(tup => {
      val url = tup._1 //用户url
      val count = tup._2 // 统计的访问数量
      val subject = new URL(url).getHost //学科
      (subject, (url, count))
    }).cache()

    //调用默认的分区器 查看分区情况 出现了多个学科信息放到同一个分区文件中 出现了碰撞
//    val partitioned: RDD[(String, (String, Int))] = subjectAndUC.partitionBy(new HashPartitioner(5))
//    partitioned.saveAsTextFile("h://out0802")


    //获取所有学科信息
    val subjects: RDD[String] = subjectAndUC.keys.distinct
    val subjectArr = subjects.collect

    //调用自定义分区器进行分区
    val partitioned: RDD[(String, (String, Int))] = subjectAndUC.partitionBy(new SubjectPartitioner(subjectArr))

    val res: RDD[(String, (String, Int))] = partitioned.mapPartitions(it => {
      it.toList.sortWith(_._2._2 > _._2._2).take(3).iterator
    })
    res.saveAsTextFile("h://out0802")

    sc.stop()
  }
}

/**
  * 自定义分区器
  */
class SubjectPartitioner(subjects:Array[String]) extends Partitioner{

  //获取分区数
  //override def numPartitions: Int = subjects.length

  //获取分区号
  //override def getPartition(key: Any): Int = {
    //用于储存学科信息和对应分区号
    val subjectAndNum = new mutable.HashMap[String,Int]()
    //计步器
    var i= 0
    for (subject <- subjects){
      subjectAndNum += (subject -> i)
      i += 1
    }
    // 获取分区数
    override def numPartitions: Int = subjects.length
    // 获取分区号
    override def getPartition(key: Any): Int = subjectAndNum.getOrElse(key.toString, 0)

//  }
}
