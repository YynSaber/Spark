package com.yyn.test_1.day02

import org.apache.spark.SparkContext

import scala.collection.GenTraversableOnce

object exercise3 {
  def main(args: Array[String]): Unit = {

    val arr = Array(1,2,3,4,5,6,7,8,9,10)
    var i = 0


    //    println(arr.sum)
//    val size: Int = arr.size
//    val p = arr.reduce((x, y) => x + y)
//    println(arr.reduce(_ + _))
//    println(arr.reduce(_ - _)) // 单线程操作
//    println(arr.par.reduce(_ - _)) // 单线程操作
//
//    println(arr.reduceLeft(_ + _))
//    println(arr.par.reduceLeft(_ + _))

    // 折叠(fold)：有初始值（无特定顺序）
    // (z: A1)(op: (A1, A1) => A1): A1
//    println(arr.fold(10)(_+_))

    val arr1 = List(List(1, 2, 3), List(3, 4, 5), List(2), List(0))
    val ints: List[Int] = arr1.reduce((x, y) => List(x.sum + y.sum))
//    println(arr1.flatten.sum)

    val ints1: List[Int] = arr1.flatMap(x=>x)
//    println(ints1)

//    val seq: Seq[Array[Int]] = Seq(Array(2,3,4),Array(1,2,3), Array(4,5,6))

//    println((seq.reduce((x, y) => Array(x(0) + y(0), x(1) + y(1), x(2) + y(2)))).toBuffer)
//    println(seq.fold(Array(0,0,0))((x, y) => Array(x(0) + y(0), x(1) + y(1), x(2) + y(2))).toBuffer)
//    println((seq.reduce((x, y) => (x zip y).map(x=>x._1+x._2))).toBuffer)
//    println((seq.fold(Array(0,0,0))((x, y) => (x zip y).map(x=>x._1+x._2))).toBuffer)




    // 求聚合值,结果为一个元组：（arr2的总和, 参与计算的数的个数） == （45，9）
    val arr2 = Array(1,2,3,4,5,6,7,8,9)
    //    println(arr2.aggregate((0,0))((x, y) => (x._1 + y, x._2 + 1), (m, n) => (m._1 + n._1, m._2 + n._2)))
        val tuple11: (Int, Int) = arr2.aggregate((0,0))((x, y) => (x._1 + y, x._2 + 1), (m, n) => (m._1 + n._1, m._2 + n._2))
    println(tuple11)


    val arr3 = List(1,2,3,4,5,6,7,8,9)
    arr3.reduce(_-_)

    val a = Array(List(2,3,4), List(1,2,3), List(4,5,6))

//    val lll: List[Int] = a.reduce((x, y)=>(x zip y).map(x=>x._1+x._2))

    //    val llla: Array[Int] = a.flatMap(x=>x)
        val array: Array[List[Int]] = a.map(x => x)
    val intqqs: List[Int] = a.reduce((x, y)=>(x:::y))













  }

}
