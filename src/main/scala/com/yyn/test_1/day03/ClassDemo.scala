package com.yyn.test_1.day03

object ClassDemo {
  def main(args: Array[String]): Unit = {
    val h=new Human
    println(h.hh)
  }

}

/**
  * 特质: trait   (接口)?
  *
  */
trait Flyable{
  // 有值的字段
  val distant:Int=100

  //没有值的字段
  val hight:Int

  //有方法提的方法
  def fly:String = "I can fly"

  //没有方法体的方法
  def fight:String

}

/**
  * 抽象类
  */

abstract class Animal{
  // 有值的字段
  val age:Int=10

  //没有值的字段
  val name:String

  //有方法体的方法
  def climb:String = "I can climb"

  //没有方法体的方法
  def run

  var hh:Int

}

/**
  * 没有继承父类情况下 如果需要实现trait 使用extends
  * 有父类时 关键字with
  */

class Human extends Animal with Flyable {


  override val name: String = "daz"

  //重写没有方法体的方法
  override def run: Unit = "I can run"

  //重写有方法体的方法
  override def climb: String = "I can climb....."

  //实现没有值的字段
  override val hight: Int = 2

  //重写没有方法体的方法
  override def fly: String = "I can not fly"

  //实现没有方法体的方法
  override def fight: String = "fight with"

  var hh:Int =1
}