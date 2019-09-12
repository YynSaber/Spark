package com.yyn.test_1.day02

object exercise {
  def main(args: Array[String]): Unit = {

    val list0 = List(1,6,3,4,5,2,7,8,9)
//    println(list0)

    val list1 = list0.map(x => x*2)
//    println(list1)

    val list2 = list0.filter(x => x%2 ==0)
//    println(list2)

    val list3 = list0.sorted
//    println(list3)

    val list4 = list0.sortBy(x=>(-x))
//    println(list4)

    val list5 = list0.sortWith((x,y) =>x > y)
//    println(list5)

    val list6 = list0.sorted.reverse
//    println(list6)

    //将list0中的元素4个一组,类型为Iterator[List[Int]]
    val it: Iterator[List[Int]] = list0.grouped(4)

    //将Iterator转换成List
    val list7: List[List[Int]] = it.toList
//    println(list7)

    //将多个list压平成一个List
//    val list8 = list7.reduce((x, y) => x ::: y)
    val list8 = list7.reduce((x,y)=>x++y)
//    println(list8)
    val list9 = list7.flatten
//    println(list9)

    val lines: List[String] = List("hello tom hello jerry", "hello suke hello", " hello tom")

    val wordCount: List[Array[String]] = lines.map(x=> x.split(" "))
    println(wordCount.toBuffer)

    val wordCount1 = wordCount.flatten
//    println(wordCount1)

    val words=lines.flatMap(x=>x.split(" "))
    println((words))

    val w=words.filter(!_.isEmpty)
    println(w)



  }
}
