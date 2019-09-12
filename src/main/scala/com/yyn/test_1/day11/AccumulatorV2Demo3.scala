package com.yyn.test_1.day11

import org.apache.spark.util.AccumulatorV2

/**
  * @title: AccumulatorV2Demo3
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/5 11:42
  */
object AccumulatorV2Demo3 {

  def main(args: Array[String]): Unit = {



  }

}
class MyAccumulator extends  AccumulatorV2[Int,Int]{
  //创建一个输出值的变量
  private var sum:Int = _

  //必须重写如下方法:
  //检测方法是否为空
  override def isZero: Boolean = sum == 0
  //拷贝一个新的累加器
  override def copy(): AccumulatorV2[Int, Int] = {
    //需要创建当前自定累加器对象
    val myaccumulator = new MyAccumulator()
    //需要将当前数据拷贝到新的累加器数据里面
    //也就是说将原有累加器中的数据拷贝到新的累加器数据中
    //ps:个人理解应该是为了数据的更新迭代
    myaccumulator.sum = this.sum
    myaccumulator
  }
  //重置一个累加器 将累加器中的数据清零
  override def reset(): Unit = sum = 0
  //每一个分区中用于添加数据的方法(分区中的数据计算)
  override def add(v: Int): Unit = {
    //v 即 分区中的数据
    //当累加器中有数据的时候需要计算累加器中的数据
    sum += v
  }
  //合并每一个分区的输出(将分区中的数进行汇总)
  override def merge(other: AccumulatorV2[Int, Int]): Unit = {
    //将每个分区中的数据进行汇总
    sum += other.value

  }
  //输出值(最终累加的值)
  override def value: Int = sum
}