package com.yyn.test_1.day11

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: AccumulatorDemo
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/5 10:20
  */
object AccumulatorDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    //2.创建SparkContext 提交SparkApp的入口
    val sc = new SparkContext(conf)

//    var sum: Int = 0

    val numRdd = sc.parallelize(List(1, 2, 3, 4, 5, 6))

//    numRdd.foreach(x => sum+=x)
//    numRdd.map(x => sum+=x)

    //Accumulator累加器实现共享变量进行聚合的过程
    val sum = sc.accumulator(0)
//    numRdd.foreach(x => sum+=x)

    //AccumulatorV2

    println(sum)

    sc.stop()
  }

}
