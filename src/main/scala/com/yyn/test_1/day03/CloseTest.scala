package com.yyn.test_1.day03

object CloseTest {

  def sum(f:Int => Int): (Int,Int)=>Int ={
    def sumF(a:Int,b:Int):Int={
      if(a>b) 0
      else f(a)+sumF(a+1,b)
    }
    sumF
  }

  def main(args: Array[String]): Unit = {
//    val sumInt: (Int, Int) => Int = sum(x=>x)
//    val res: Int = sumInt(1,2)

    println(sum(x => x)(1, 2))
//    println(res)
  }
}
