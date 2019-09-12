package com.yyn.test_1.day04

import scala.io.Source

/**
  * @title: RichFile
  * @projectName test_1
  * @author YongningY
  * @date 2019/7/26 21:23
  */
class RichFile(val file:String) {

  def read():String= {
    Source.fromFile(file).mkString
  }

}
object RichFile{
  def main(args: Array[String]): Unit = {
    val file="h://test.txt"

    //显式的实现对file的方法增强
//    val richFile = new RichFile(file)
//    val content:String=richFile.read()
//    println(content)

    //隐式的实现对file的方法增强,用到隐式转换函数
    import MyPredef.fileToRichFile
    val content:String=file.read()
    println(content)


  }
}