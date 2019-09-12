package com.yyn.test

import scala.io.Source

object Files {

  def main(args: Array[String]): Unit = {


    val strings2 = Source.fromFile("h://test.txt").getLines()
    //  var j = 0
    //  var l = 1
    //  for(i<-strings2){
    //    j=j+1
    //    println(i)
    //    val bool: Boolean = i.split(" ").contains("Spark")
    //    if(bool){
    //      println(s"第${l}行出现spark")
    //    }
    //    l = l+1
    //  }
    //  println(s"文件共${j}行")

      val strings = Source.fromFile("h://test.txt").getLines().mkString
      val stringToInt: Map[String, Int] = strings.split(" ").map((_,1)).groupBy(_._1).map(x=>(x._1,x._2.size))
      println("Spark共出现了"+stringToInt.get("Spark")+"次")
//    val strings1 = Source.fromFile("h://test.txt").getLines().mkString
//    val strings3: Array[String] = strings1.split("'\n")
//    for (i <- strings2) {
//      println(i)
//    }
  }
}
