package com.yyn.trys

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
  * @title: Test1
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/15 16:21
  */
object Test1 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
    val sc = new SparkContext(conf)

    val logs: RDD[String] = sc.textFile("dir/test1.txt")

//    println(logs.collect.toBuffer)

    var q:Int = 0
    val res: RDD[(Int, Int)] = logs.map(x => {
      val p = x.split(" ")
      q += 1
      (q, p.length)
    })

    println(res.collect.toBuffer)

    sc.stop()

  }

}
