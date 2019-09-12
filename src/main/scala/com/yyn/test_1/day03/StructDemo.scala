package com.yyn.test_1.day03

/**
  * 构造器 (构造方法)
  * 主构造器的声明方式  在主类后面加参数列表
  * 主构造器的参数列表里可以放多个参数 杂而叶参数往往作为初始化
  * 构造参数中 val修饰不可改变 var 修饰可以后期改变
  * facevalue没有val或var修饰 默认val 不可改变
  * facevalue构造参数只能在本类访问 伴生对象也无法访问
  */
class StructDemo(val name:String, var age : Int, faceValue:Int = 60) {

//  faceValue=90
  def getface:Int={
    faceValue
  }

  var gender:String=_
  //辅助构造器
  def this(name:String,age:Int,gender:String){
    this(name,age)
    this.gender=gender
  }
}



object StructDemo{

  def main(args: Array[String]): Unit = {
    val sd=new StructDemo("hua",19)
    println(sd.name)
    println(sd.getface)
  }

}
