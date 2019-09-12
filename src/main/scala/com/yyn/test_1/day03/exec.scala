package com.yyn.test_1.day03

object exec {

  def main(args: Array[String]): Unit = {
    var list = List(1,2,3,4,5,6)
   // println(list.reduce((x, y) => x + y))
    println(list.fold(0)((x, y) => x + y))
  }

}
