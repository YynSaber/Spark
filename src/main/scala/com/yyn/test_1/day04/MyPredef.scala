package com.yyn.test_1.day04

/**
  * @title: MyPredef
  * @projectName test_1
  * @author YongningY
  * @date 2019/7/26 20:47
  */
object MyPredef {

  implicit val a ="123"
  implicit val b="ijn"
  implicit val c=11

  implicit val fileToRichFile = (file:String)=>new RichFile(file)

}
