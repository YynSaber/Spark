package com.yyn.test0727

import com.yyn.test.ObjectObjs

/**
  * @title: test02
  * @projectName test_1
  * @author 杨永宁
  * @date 2019/7/27 15:41
  */
object test02 {

  def main(args: Array[String]): Unit = {

    val per = new Person("001", "yiyi", 18)

    println(per.id + "号 " + per.name + " 今年 " + per.age + " 岁")

    per.eat()
    per.can()
    per.study()
  }

}
//特质
trait character{
  def eat()
}

//抽象类
abstract class Animal{

  def can():Unit = {
    println("I can")
  }
}

//构造器  & 继承
class Person(var id:String,val name:String,var age:Int) extends Animal with character {

  var score:String=_

  def this(id:String,name:String,score:String){
    this(id,name,age=1)
    this.score=score
  }

  override def eat(): Unit = {
    println("I can eat")
  }

  override def can(): Unit = super.can()

  def study(): Unit ={
    println(name +" is " +Person.status)
  }
}

//伴生对象
object Person{

  private val status:String="Studying"

}