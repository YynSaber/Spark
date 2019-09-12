package com.yyn.test_1.day03.MatchString

import scala.util.Random

class MatchClass {


}
object MatchClass{
  def main(args: Array[String]): Unit = {
    val matchClass=new MatchClass
    val arr =Array("tingjie",18,true,matchClass)

    val ele =arr(Random.nextInt(arr.length))

    println(ele)

    ele match {
      case str:String=>println("String")
      case int:Int=>println("Int")
      case bool:Boolean=>println("Boolean")
      case matchClass:String=>println("matchClass")
  }

  }
}