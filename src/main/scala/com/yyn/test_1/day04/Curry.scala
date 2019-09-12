package com.yyn.test_1.day04

/**
  */
object Curry {

//  implicit var hh="123"

  def curry(str:String)(implicit name :String="hua" ):String={
    str+name
  }

  def main(args: Array[String]): Unit = {
    import MyPredef.b
    println(curry("hi"))

  }

}
