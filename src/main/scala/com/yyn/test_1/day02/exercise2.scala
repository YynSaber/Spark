package com.yyn.test_1.day02

import scala.collection.mutable.ArrayBuffer

object exercise2 {
  def main(args: Array[String]): Unit = {
    val arr=Array(1,2,3,4,5,6)
//    println(arr.toBuffer)

    val maparr:Array[Int] = arr.map(x => x*2)

//    println(maparr.toBuffer)
//    maparr.foreach(println)

    val ab = ArrayBuffer[Int]()
    ab.insert(0, 2)
    ab += 1
//    println(ab.toBuffer)

    val arr1=Array(2,3,5,4,6,1)
//    println(arr1.sorted.toBuffer)

//    println(arr1.sortBy(x=>x).toBuffer)

//    println(arr1.sortWith(_<_).toBuffer)

    val arr2=Array(("a",3),("c",1),("b",2))

    //默认以第一个元素排序
//    println(arr2.sorted.toBuffer)
    //定义以第二个元素排序
//    println(arr2.sortBy(_._2).toBuffer)

//    println(arr2.sortWith(_._2>_._2).toBuffer)

//    println(m1(2))



  }

  def m1(x: Int): Int = {

    // 方法中，会将最后一行代码的值作为返回，但是，如果是赋值的过程，是不能作为返回
    val sum = x * x
    sum
  }
}
