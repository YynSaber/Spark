package com.yyn.test_1.day03

//类名和类文件名可以不统一
class Person {

  //用var修饰字段相当于有get方法也有set方法
  // 初始值"_" 变量必须var修饰 且必须手动给类型
  //如果 Int初始为0  String 初始null
  //声明属性的时候 可以不用get set 方法 利用var val的特性就可以实现
  var id:Int = _
  var name:String = _

  //用val修饰字段相当于只有get方法 没有set方法
  val addr:String="ningyang"

  //private 私有字段 只能在本类和其伴生对象中使用
  private var age : Int =_

  //用private[this]修饰的字段成为对象私有字段 只能在本类使用
  private [this] val gender = "meal"

  //如果像访问对象私有字段 声明一个获取对象私有字段的方法
  def getgender(): String ={
    gender
  }

}

object Person{
  def main(args: Array[String]): Unit = {
    val p = new Person
    p.id=1
    p.name="hua"
    println(p.id)
    println(p.name)
//    println(p.addr)

    p.age=18

    println(p.getgender)

  }
}

//可以声明多个类
//单例对象 此类不是伴生对象
class PersonTest{
  def main(args: Array[String]): Unit = {
    val p = new Person


  }
}
