package com.yyn.test_1.day09

/**
  * @title: HashTest1
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/2 14:49
  */
object HashTest1 {

  def main(args: Array[String]): Unit = {
    val key="an"
    val numPartitions=3

    val row = key.hashCode.toInt % numPartitions
    val num = row+(if(row<0) numPartitions else 0)
    println(num)
  }

}
