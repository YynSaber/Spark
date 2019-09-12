package com.yyn.test_1.day05

/**
  * @title: exer
  * @projectName test_1
  * @author YongningY
  * @date 2019/7/27 9:10
  */
object exer {

  def main(args: Array[String]): Unit = {
    val line = List("aaa nnn","qqq ppp","mmm nnn")
    val words: List[String] = line.flatMap(_.split(" "))

    val tuples: List[(String, Int)] = words .map((_,1))

    val group: Map[String, List[(String, Int)]] = tuples.groupBy(_._1)

    val sum: Map[String, Int] = group.mapValues(_.size)

    println(sum)
  }

}
