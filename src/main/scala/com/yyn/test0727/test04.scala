package com.yyn.test0727

import scala.io.Source

/**
  * @title: test04
  * @projectName test_1
  * @author 杨永宁
  * @date 2019/7/27 16:16
  */
object test04 {

  def main(args: Array[String]): Unit = {

    val lines: Iterator[String] = Source.fromFile("h://test.txt").getLines()

    var p=0
    var q=0
    for(i <- lines){
      p+=1
      if(i.split("[ ]").contains("Spark")){
        q+=1
        println("第 "+q+" 行含Spark")
      }
    }
    println("文件有 "+p+" 行")
    println("含Spark的有 "+q +" 行")

    val string: String = Source.fromFile("h://test.txt").getLines().mkString

    val arr: Array[String] = string.split("[ .\n]").filter(!_.isEmpty)
    println(arr.toBuffer)

    val size: Int = arr.filter(x =>(x.contains("Spark"))).size

    println("Spark单词的个数:"+size)

  }
}
