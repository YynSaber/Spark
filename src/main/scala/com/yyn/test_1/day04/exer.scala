package com.yyn.test_1.day04

/**
  * @title: exer
  * @projectName test_1
  * @author YongningY
  * @date 2019/7/26 16:39
  */
object exer {

  def main(args: Array[String]): Unit = {
    val words = Seq("apple", "dog", "table")
//    println(words.foldLeft(0)((resultLength, word) => resultLength + word.length))

    val result1 = (0 to 10000).map{case _ => Thread.currentThread.getName}.distinct
    val result2 = (0 to 10000).par.map{case _ => Thread.currentThread.getName}.distinct
//    println(result1)
//    println(result2)


    val lines = List("hello tom hello jerry", "hello suke hello", " hello tom")
    //先按空格切分，再压平
    //    val words = lines.map(_.split(" "))
    //    val flatWords = words.flatten
    val flatWords = lines.flatMap(_.split(" "))
//    println(flatWords)

    val arr1 = List(List(1, 2, 3), List(3, 4, 5), List(2), List(0))
    val res = arr1.flatten.reduce(_+_)
//    println(res)

    val books = Seq(("Future of Scala developers", 85),
      ("Parallel algorithms", 240),
      ("Object Oriented Programming", 130),
      ("Mobile Development", 495) )

    println(books.filter(x => x._2 > 120))
  }

}
