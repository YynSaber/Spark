package com.yyn.test

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: suCache
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/1 14:26
  */
object suCache {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")

    val sc = new SparkContext(conf)

    //五个学科信息
    val sus=Array("http://android.learn.com","http://ui.learn.com","http://java.learn.com","http://h5.learn.com","http://bigdata.learn.com")

    val tuples: RDD[(String, Int)] =
      sc.textFile("H://idea/access.txt").
        map(line => (line.split("\t")(1),1))

    val aggred: RDD[(String, Int)] = tuples.reduceByKey(_+_).persist(StorageLevel.MEMORY_AND_DISK) //.cache()

    for(su <- sus){

      val filtered: RDD[(String, Int)] = aggred.filter(_._1.startsWith(su))
      //按照学科进行过滤,得到学科对应所有的模块的访问量
      val res: Array[(String, Int)] = filtered.sortBy(_._2,false).take(3)
      println(res.toBuffer)
    }

    sc.stop()
  }

}
