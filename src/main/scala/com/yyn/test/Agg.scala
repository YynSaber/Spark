package com.yyn.test

object Agg {
  def main(args: Array[String]): Unit = {
    val arr: Array[Array[Int]] = Array(Array(2,3,4),Array(1,2,3), Array(4,5,6))

    println((arr.reduce((x, y) => (x zip y).map(x=>x._1+x._2))).toBuffer)
    println((arr.fold(Array(0,0,0))((x, y) => (x zip y).map(x=>x._1+x._2))).toBuffer)

//    val list: List[List[Int]] = List(List(2,3,4),List(1,2,3), List(4,5,6))
//    println((list.reduce((x, y) => (x zip y).map(x=>x._1+x._2))).toBuffer)

  }


}
