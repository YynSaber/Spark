package com.yyn.test_1.day02

object test02 {
  def main(arg:Array[String]): Unit ={
//    val arr = Array(1,2,3,4,5,6)

//    val mapedArr:Array[Int]=arr.map(x=>x)

//    println(mapedArr.toBuffer)
//    mapedArr.foreach(println)

//    println(m1(1))

    val Array(x,y,z) =Array("A",false,11)

//    println((x,y,z))

    val (m,n,l)=("A",false,11)
//    println(m,n,l)

    val arr1 = Array(2,3,5,4,6,1)
    println(arr1.sortBy(x=>x).toBuffer)

    var arr = Array(("a",3),("b",1),("c",2))
    var sortAdd = arr.sortWith(_._2>_._2)
//    println(sortAdd.toBuffer)

  }

  def m1(x:Int):Int={
    val sum = x*x
    sum
  }
}
