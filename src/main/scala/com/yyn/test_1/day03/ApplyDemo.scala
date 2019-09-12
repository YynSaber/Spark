package com.yyn.test_1.day03

/**
  * apply方法只能声明在主类的伴生对象中
  * apply方法通常成为注入方法
  * 用apply做一些初始化的操作
  * apply方法的参数列表和主类的构造器的参数可以不统一
  *
  * unapply方法被称为提取方法 使用unapply方法可以提取固定的值或者对象
  * unapply方法会返回一个option对象 如果Option有值,会返回一个Some对象来封装及具体的值 如果没有值返回None对象
  * apply和unapply方法哦都是隐式调用
  */

class ApplyDemo(val name:String,val age:Int, val faceValue:Int=60) {

}

object ApplyDemo{
  def apply(name: String, age: Int): ApplyDemo = {
    new ApplyDemo(name, age)
  }

  //提取方法
  def unapply(applyDemo: ApplyDemo): Option[(String, Int, Int )] ={
    if(applyDemo==null){
      None
    }
    else{
      Some(applyDemo.name,applyDemo.age,applyDemo.faceValue)
    }
  }
}

object  ApplyTest{
  def main(args: Array[String]): Unit = {
    val applyDemo=ApplyDemo("xiaoli",20)

    //调用unapply
    applyDemo match {
      case ApplyDemo(name,age,faceValue)=>println(s"name:$name,age:$age,facevalue: $faceValue")
      case _ => println("no")


    }
  }
}
