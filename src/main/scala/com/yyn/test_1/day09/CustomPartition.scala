package com.yyn.test_1.day09

import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
  * @title: CustomPartition
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/2 14:18
  */
class CustomPartiton(numPartiton:Int) extends Partitioner{
  // 返回分区的总数
  override def numPartitions: Int = numPartiton
  // 根据传入的Key返回分区的索引
  override def getPartition(key: Any): Int = {
    key.toString.toInt % numPartiton
  }
}
object CustomerPartitoner {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("CustomerPartitoner").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    //zipWithIndex该函数将RDD中的元素和这个元素在RDD中的ID（索引号）组合成键/值对。
    val rdd = sc.parallelize(0 to 10, 3).zipWithIndex()
    println(rdd.collect.toBuffer)
    val func = (index: Int, iter: Iterator[(Int, Long)]) => {
      iter.map(x => "[partID:" + index + ", value:" + x + "]")
    }
    val r = rdd.mapPartitionsWithIndex(func).collect()
//    for (i <- r) {
//      println(i)
//    }
    r.foreach(println)
    val rdd2 = rdd.partitionBy(new CustomPartiton(5))
    val r1 = rdd2.mapPartitionsWithIndex(func).collect()
    println("----------------------------------------")
//    for (i <- r1) {
//      println(i)
//    }
    r1.foreach(println)
    println("----------------------------------------")
    sc.stop()
  }
}