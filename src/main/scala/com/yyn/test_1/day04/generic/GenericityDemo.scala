package com.yyn.test_1.day04.generic

/**
  * @title: Teacher
  * @projectName test_1
  * @description: TODO
  * @author YongningY
  * @date 2019/7/26 14:31
  */
object Teacher {
  def main(args: Array[String]): Unit = {
    val t1=new Teacher("dazhao",95)
    val t2=new Teacher("dong",99)

    val arr=Array(t1,t2)

//    val sorted:Array[Teacher]=arr.sorted.reverse

//    println(sorted(0).name)
  }
}

class Teacher(val name:String, val faceValue:Int){

}
class GenericityDemo{

}
