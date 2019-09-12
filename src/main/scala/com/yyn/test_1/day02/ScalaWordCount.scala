package com.yyn.test_1.day02

object ScalaWordCount {
  def main(args: Array[String]): Unit = {
    val lines = List("hello tom hello jerry","hello suke hello","hello tom")

    //将数据切分  生成一个个的单词
    val words:List[String] = lines.flatMap(_.split(" "))

    //空字符串过滤
    val filter: List[String] = words.filter(!_.isEmpty)

    //将每个单词生成一个元组: (word, 1)
    //words.map(x=>(x,1))

    val tuples:List[(String,Int)] = filter.map((_,1))

    //按照相同的单词进行分组
    val grouped:Map[String,List[(String,Int)]] = tuples.groupBy(_._1)

    //将相同单词的数据进行个数统计
    // val sumed: Map[String, Int] = grouped.map(x => (x._1, x._2.size))

    val sumed1: Map[String, Int] = grouped.mapValues(_.size)
    //降序排序
    //map没有提供排序方法 将map转换为List在排序
    val sorted: List[(String, Int)] = sumed1.toList.sortBy(_._2)
    println(sorted)

//    lines.flatMap(_.split(" ")).filter(!_.isEmpty).map((_,1)).groupBy(_,1)

    val aa: List[(String, Int)] = lines.flatMap(_.split(" ").groupBy(x=>x).mapValues(_.size))
    println(aa)

  }
}
