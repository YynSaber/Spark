package com.yyn.trys

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: Tests
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/15 16:52
  */
object Tests {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("fd").setMaster("local[2]")
    val sc = new SparkContext(conf)
    var i=0
    val line = sc.textFile("dir/123.txt").map(x=>{
      val word: Array[String] = x.split(" ")
      i+=1
      (x,word.length,i)
    })
//    line.foreach(println)

    println(line.collect.toBuffer)
  }

}
