package com.yyn.test_1.day11

import org.apache.spark.util.{DoubleAccumulator, LongAccumulator}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: AccumulatorV2Demo
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/5 10:46
  */
object AccumulatorV2Demo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkWordCount").setMaster("local[2]")
    //2.创建SparkContext 提交SparkApp的入口
    val sc = new SparkContext(conf)

    //    var sum: Int = 0

//    val num1 = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9), 2)

    val num2 = sc.parallelize(List(1.2, 2.3, 3.4, 4.5, 5.6, 6, 7, 8, 9), 2)


    //首先进行注册 注册并初始化一个累加器
//    def longAcc(name : String): LongAccumulator ={
//      val acc = new LongAccumulator
//      sc.register(acc,name)
//      acc
//    }

    def longAcc(name : String): DoubleAccumulator ={
      val acc = new DoubleAccumulator
      sc.register(acc,name)
      acc
    }

//    累加器返回值
    val acc1 :DoubleAccumulator = longAcc("DoubleAcc")
    num2.foreach(x => acc1.add(x))
    println(acc1.value)

    sc.stop()
  }

}
