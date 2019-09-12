package com.yyn.test0802

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * @title: test01
  * @projectName test_1
  * @author 杨永宁
  * @date 2019/8/2 15:29
  */
object test01 {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("cdn").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    //读取数据并切分
    val logs: RDD[String] = sc.textFile("H://idea/cdn.txt").cache()

//    flowOfHour(logs)
    videoIpStatics(logs)
//    ipStatics(logs)

    sc.stop()

  }

  //统计独立IP数top10
  def flowOfHour(data: RDD[String]): Unit = {
    val line: RDD[Array[String]] = data.map(_.split(" "))

    val ip: RDD[(String, Int)] = line.map(x=>(x(0),1))

    val ipNum: RDD[(String, Int)] = ip.reduceByKey(_+_)

    val res: Array[(String, Int)] = ipNum.sortBy(-_._2).take(10)

    println(res.toBuffer)

  }

  // 统计每个视频独立IP数

//  def videoIpStatics(data: RDD[String]) ={
//
//    val nameAndIP: RDD[(String, String)] = data.map(line => {
//      val fields = line.split(" ")
//      val urls = fields(6).split("/")
//      val videoName = urls(urls.length - 1)
//
//      (videoName, fields(0))
//
//    })
//    val filterData: RDD[(String, String)] = nameAndIP.filter(x => x._1.endsWith(".mp4"))
//
//    val grouped: RDD[(String, Iterable[String])] = filterData.groupByKey()
//
//    val distincted: RDD[(String, Int)] = grouped.mapValues(_.toList.distinct.size)
//
//    val res: RDD[(String, Int)] = distincted.sortBy(_._2,false)
//
//    val collect: Array[(String, Int)] = res.collect
//
//    collect.foreach(x => println(x._1+"："+x._2))
//  }



  def videoIpStatics(data: RDD[String]): Unit = {

    val line: RDD[Array[String]] = data.map(_.split(" "))

    val fileIp: RDD[(String, String)] = line.map(x => {

      val file = x(6)

      val ip = x(0)
      (file, ip)
    })

    val filtered: RDD[(String, String)] = fileIp.filter(_._1.endsWith("mp4"))

    val filesIp: RDD[(String, String)] = filtered.map(x => {
      val spilted: Array[String] = x._1.split("/")

      val file = spilted(3)
      val ip = x._2
      (file, ip)
    })

    val fileIps: RDD[(String, Iterable[String])] = filesIp.groupByKey()

    val distincted: RDD[(String, Int)] = fileIps.mapValues(_.toList.distinct.size)


    //    val res: RDD[(String, Int)] = distincted.sortBy(-_._2)
        val res: Array[(String, Int)] = distincted.sortBy(-_._2).take(10)

//    val value: RDD[String] = res.map(x=>"ss"+x._1+"ssa"+x._2)

//    res.collect.foreach(x => println(x._1+"哈哈哈"+x._2))

    res.foreach(x => println(x._1+"哈哈哈"+x._2))
  }

  // 统计独立IP访问量前10位
  def ipStatics(data: RDD[String]): Unit = {

    val line: RDD[Array[String]] = data.map(_.split(" "))

    val timeSum: RDD[(String, Long)] = line.map(x => {
      val time = x(3).split(":")(1)
      val sum = x(9).toLong
      (time, sum)
    })

    val aggr: RDD[(String, Long)] = timeSum.reduceByKey(_+_)

    val res: RDD[(String, Long)] = aggr.map(x => (x._1,x._2/1024/1024/1024)).sortBy(_._1)

    println(res.collect.toBuffer)

  }

}
