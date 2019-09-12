package com.yyn.test_1.day03.MatchString

import scala.util.Random

/**
  * 样例类 就是用case class声明的类
  * 样例类分两种 case object和case class
  * 其中 case object是没有构造参数的 case class可以放构造参数的
  * 总结样例类的作用
  *   1.和模式匹配搭配使用  使得代码更加灵活
  *   样例类case class可以封装多个值到样例类中,也就是起到封装的效果
  */

object CaseClassDemo {
  def main(args: Array[String]): Unit = {
    val arr=Array(CheckTimeOutWorker,SubmitTask("001","task_001"),HeatBeat(10000))

    arr(Random.nextInt(arr.length)) match{
      case CheckTimeOutWorker=>println("Check")
      case SubmitTask(id,task)=>println("submit")
      case HeatBeat(time)=>println("Heat")
      case _=>println("no")

    }



  }
}

case object CheckTimeOutWorker

case class SubmitTask(id:String,task: String)

case class HeatBeat(time :Long)