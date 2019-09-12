package com.yyn.test

object ObjectObj {
  def main(args: Array[String]): Unit = {

    val oos = new ObjectObjs("001","yiyi",18)
    println(oos.id +"号" + oos.name +"今年" + oos.age + "岁")

    print(oos.name)
    oos.eat()
    oos.can

    oos.study()
  }
}

//特质
trait character{
  def eat()
}

//抽象类
abstract class Animal{
  //有方法体的方法
  def can():Unit = {
    println("I can")
  }
}


//构造器  & 继承
class ObjectObjs(var id:String,val name:String,var age:Int) extends Animal with character {

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
    println(name +" is " +ObjectObjs.status)
  }
}

//伴生对象
object ObjectObjs{

  private val status:String="Studying"

}


