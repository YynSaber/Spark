package com.yyn.test

import scala.io.Source

object File {
  def main(args: Array[String]): Unit = {
    val file: Iterator[String] = Source.fromFile("h://test.txt").getLines()

    var p = 0
    var q = 1
    for(i <- file){
      p+=1
      val bool = i.split(" ").contains("Spark")
      if(bool){
        println(s"第 $q 行出现了 Spark")
        q+=1
      }

    }
    println(s"文件有 $p 行")

    val string: String = Source.fromFile("h://test.txt").getLines().mkString

    val arr: Array[String] = string.split("[ .\n]").filter(!_.isEmpty)

    println(arr.toBuffer)

    val size: Int = arr.filter(x =>(x.equals("Spark"))).size
    println(size)

//    var n=0
//    for(i <- arr){
//      if(i.equals("Spark")){
//        n+=1
//      }
//      println(i)
//    }
//
//    println("Spark共出现了"+ n +"次")


  }
}
