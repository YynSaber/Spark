package com.yyn.test_1.day02

class ScalaLazy{

}

object ScalaLazy {

  def init(): Unit ={
    println("init")
  }

  def main(args: Array[String]): Unit = {

    val prop=init()

    println("after")

    println(prop)
  }
}

object ScalaLazy1 {

  def init(): Unit ={
    println("init")
  }

  def main(args: Array[String]): Unit = {

    lazy val prop=init()

    println("after")

    println(prop)
  }
}