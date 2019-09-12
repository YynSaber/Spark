package com.yyn.test_1.day14

import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: StreamingWC
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/9 16:35
  */
object StreamingWC {

  def main(args: Array[String]): Unit = {

    //初始化
    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
    val sc = new SparkContext(conf)
    //实例化Steaming的上下文
    val ssc: StreamingContext = new StreamingContext(sc, Durations.seconds(5))

    //从NetCt服务里获取数据
    val logs: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop01",6666)

    val res=logs.flatMap(_.split(" ").map((_,1))).reduceByKey(_+_)

    res.print()

    ssc.start()  //提交任务到集群
    ssc.awaitTermination()  //线程等待

  }

}
