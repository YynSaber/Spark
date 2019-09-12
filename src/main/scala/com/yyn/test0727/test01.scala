package com.yyn.test0727

import scala.collection.mutable

/**
  * @title: test01
  * @projectName test_1
  * @author 杨永宁
  * @date 2019/7/27 15:28
  */
object test01 {
  def main(args: Array[String]): Unit = {

    val list= List("Apache" -> "Spark", "Apache" -> "Kafka", "Oracle" -> "JAVA", "Oracle" -> "DB ORACLE", "Oracle" -> "Mysql")

    val maps: Map[String, List[(String, String)]] = list.groupBy(x => x._1)

    println(maps)

    val pro: Map[String, List[String]] = maps.mapValues(x => x.map(_._2))
//    val pro: Map[String, Int] = maps.mapValues(x => x.size)
//    println(pro)

    val arr = Array((2,3,4), (1,2,3), (4,5,6))

//    arr.flatMap(x=>x.map(y=>y))
//    println(ints.toBuffer)


    val l1 = list.groupBy(_._1).map(_._2).flatten


  }

}
