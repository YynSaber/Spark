package com.yyn.test_1.day08

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: CheckPointDemo
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/1 15:25
  *
  *    checkpoint的步骤
  *      设置checkpoint的存储目录
  *      cache
  *      开始checkpoint
  *
  */
object CheckPointDemo {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("SparkDemo").setMaster("local")
    val sc = new SparkContext(conf)
    sc.setCheckpointDir("hdfs://hadoop02:8020/ck")
    val rdd =  sc.textFile("hdfs://hadoop02:8020/file").flatMap(_.split("   ")).map((_,1)).reduceByKey(_+_)
    //检查点的触发一定要使用个action算子

    //checkpoint
    rdd.checkpoint()

    //panduan是否chheckpoint
    val iscp =rdd.isCheckpointed


    rdd.saveAsTextFile("hdfs://hadoop02:8020/out11")

    val iscp1 =rdd.isCheckpointed

    //查看checkpoint的目录
    val cpfile = rdd.getCheckpointFile

    println(iscp)
    println(iscp1)
    println(cpfile) //查看存储的位置

    sc.stop()

  }

}
