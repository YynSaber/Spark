package com.yyn.test_1.day03

/**
  * 和主类名相同  用object修饰的类称作主类的伴生对象
  * 单例对象包含伴生对象
  * 主类和他的伴生对象可以相互访问私有字段
  */

class CompanionOb {
  var id =0
  private val name="tingjie"
  def printContent(): Unit ={
    println(name+CompanionOb.content)
  }
}

object CompanionOb{

  private val content ="is my goddess"

  def main(args: Array[String]): Unit = {

    val co=new CompanionOb
    co.id=10
    println(co.id)

    println(co.name)

    co.printContent()

  }

}
