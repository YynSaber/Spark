package com.yyn.test_1.day09

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: CustomSorted
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/2 9:42
  */
//object CustomSort {
//
//  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
//    val sc = new SparkContext(conf)
//
//    val userInfo: RDD[String] = sc.parallelize(Array("mi 25 85","bing 30 85","yuan 18 90"))
//    val personRdd: RDD[(String, Int, Int)] = userInfo.map(x => {
//      val arr = x.split(" ")
//      val name = arr(0)
//      val age = arr(1).toInt
//      val fv = arr(2).toInt
//      (name, age, fv)
//    })
//    //排序
//    //
//    val sorted = personRdd.sortBy(x => new Person(x._1, x._2, x._3))
//
//    println(sorted.collect.toBuffer)
//  }
//
//}

////普通实现类实现自定义排序 需要实现 Ordered特质 需要序列化
//class  Person(val name:String,val age :Int, val fv:Int)
//  extends Serializable with Ordered[Person] {
//
//  override def compare(that:Person)={
//    if(this.fv!=that.fv){
//      that.fv-this.fv  //降
//    }else{
//      this.age-that.age
//    }
//  }
//  override def toString : String = s"$name,$age,$fv"
//}

//样例类不需要new 不需要序列化
//case class  Person(val name:String,val age :Int, val fv:Int)
//  extends Ordered[Person] {
//
//  override def compare(that:Person)={
//    if(this.fv!=that.fv){
//      that.fv-this.fv  //降
//    }else{
//      this.age-that.age
//    }
//  }
//}

