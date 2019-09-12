package com.yyn.test0727

/**
  * @title: test03
  * @projectName test_1
  * @author 杨永宁
  * @date 2019/7/27 16:06
  */
object test03 {
  def main(args: Array[String]): Unit = {
    val arr = Array(List(2,3,4), List(1,2,3), List(4,5,6))

    val nums: List[Int] = arr.fold(List(0,0,0))((x, y) => (x zip y).map(x => x._1 + x._2))

    println(nums)


  }


}
