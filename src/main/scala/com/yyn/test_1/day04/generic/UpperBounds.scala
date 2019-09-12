package com.yyn.test_1.day04.generic

/**
  * @title: UpperBounds
  *        上界
  * @projectName test_1
  * @description: TODO
  * @author YongningY
  * @date 2019/7/26 14:38
  */
class UpperBounds[T<:Comparable[T]] {
  //比较的方法
  def chooser(first:T,second:T):T={
    if(first.compareTo(second) > 0) first else second
  }
}

object  Chooser{
  def main(args: Array[String]): Unit = {
    val chooser=new UpperBounds[Girl]

    val g1 = new Girl("mi",95,30)
    val g2 = new Girl("bing",90,33)

    val girl = chooser.chooser(g1,g2)

    println(girl.name)

  }
}

class Girl(val name:String,val faceValue:Int,val age:Int) extends Comparable[Girl] {
  override def compareTo(that:Girl): Int ={
    if(this.faceValue==that.faceValue){
      that.age-that.age
    }
    else {
      this.age-that.age
    }
  }
}