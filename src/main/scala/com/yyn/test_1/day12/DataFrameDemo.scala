package com.yyn.test_1.day12

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * 生成并操作DataFrame
  * 将RDD转换为DataFrame
  */
object DataFrameDemo {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    //获取数据
    val peopleRdd = sc.textFile("dir/people.txt")

    //切分
    val linesRdd: RDD[Array[String]] = peopleRdd.map(_.split(","))
    //将数据装换成tuple 便于DataFrame转换
    val tupRdd: RDD[(String, Int)] = linesRdd.map(x => (x(0),x(1).toInt))

    println(tupRdd.collect.toBuffer)

    //SparkSession上下文
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    //引用sqlContext上下文
    import spark.implicits._
    //toDF方法属于SQLContext上下文的方法 在RDD转换为DataFrame时 需要我们指定列名
    val df: DataFrame = tupRdd.toDF("name","age")

    df.show()

    sc.stop()
    spark.stop()


  }

}
