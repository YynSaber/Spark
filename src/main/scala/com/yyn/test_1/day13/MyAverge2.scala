package com.yyn.test_1.day13

import org.apache.spark.sql.Encoder
import org.apache.spark.sql.expressions.Aggregator


/**
  * @title: MyAverge2
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/7 9:45
  */
class MyAverge2 extends Aggregator[Employee,Averge,Double]{
  override def zero: Averge = ???

  override def reduce(b: Averge, a: Employee): Averge = ???

  override def merge(b1: Averge, b2: Averge): Averge = ???

  override def finish(reduction: Averge): Double = ???

  override def bufferEncoder: Encoder[Averge] = ???

  override def outputEncoder: Encoder[Double] = ???
}
case class Employee(salary:Double)
case class Averge(var sum:Double,var count:Double)