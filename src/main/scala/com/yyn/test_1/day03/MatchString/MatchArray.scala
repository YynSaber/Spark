package com.yyn.test_1.day03.MatchString

object MatchArray {
  def main(args: Array[String]): Unit = {
//    val arr=Array(2,5,7,8)
//
//    arr match {
//      case Array(3,b,c,a) => println("case 1")
//      case Array(3,_*) => println("case 2")
//      case Array(_,_,_,_) => println("no")
//
//    }

//    val tuo = (2,3,5,8)
//    tuo match {
//      case (3,a,b,c) => println("case 1")
//      case (_,x,y,z) => println("case 2")
//      case _ => println("no")
//    }

    val list =List(2,3,5,6)
    list match {
      case 1::Nil=>println("case 1")
      case a::b::c::d::Nil => println("case 2")
      case 1::List(x,y,z) => println("case 3")
      case _ => println("no")
    }

  }

}
