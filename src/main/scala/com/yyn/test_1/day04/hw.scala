package com.yyn.test_1.day04

/**
  * @title: hw
  * @projectName test_1
  * @author YongningY
  * @date 2019/7/26 16:33
  */
object hw {

  def values(fun:(Int)=>Int,low:Int,high:Int): Unit ={

    var list = List[(Int,Int)]()

    for(i <- low to high){
      list=(i,fun(i))::list
    }

    println(list.sorted)

  }

  def main(args: Array[String]): Unit = {
    values(x=>x*x,-5,5)

  }

}
