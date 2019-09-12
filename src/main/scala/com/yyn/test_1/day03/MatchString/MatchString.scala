package com.yyn.test_1.day03.MatchString

import scala.util.Random

object MatchString {
  def main(args: Array[String]): Unit = {
    val arr=Array("zhou","yangzi","guan","zheng")

    val name=arr(Random.nextInt(arr.length))

    println(name)

    name match{
      case "zhou" => println("周")
      case "yangzi"=>println("杨")
      case "guan" => println("关")
      case "zheng" => println("郑")
    }
  }
}
