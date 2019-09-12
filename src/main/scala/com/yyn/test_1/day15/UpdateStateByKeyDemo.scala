package com.yyn.test_1.day15

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Durations, Seconds, StreamingContext}

/**
  * updateStateByKey 会将历史结果拿到当前批次进行进一步计算
  * 注意 updateStateByKey 没有储存历史结果的功能
  * 所以updateStateByKey只能去checkpoint的目录去拿历史结果
  */
object UpdateStateByKeyDemo {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
    val ssc =new StreamingContext(conf,Seconds(5))  //Durations.seconds(5) == Seconds

    //checkpoint   hdfs
    ssc.checkpoint("d://cp_0810_1")

    //获取数据
    val logs = ssc.socketTextStream("hadoop01",6666)

    //开始统计
    /**
      * def updateStateByKey[S: ClassTag](
      * updateFunc: (Iterator[(K, Seq[V], Option[S])]) => Iterator[(K, S)]
      *   函数的参数中
      *       k是进行聚合是的那个相同的key  现在指单词
      *       Seq[V]是指当前[V]批次相同key对应的value 会将相同key对应的value统计的放到Seq里
      *       Option[S]指历史结果相同的key对应的结果数据 可能有值 也可能没有值 所以用option来封装
      * partitioner: Partitioner
      *   指指定分区器
      * rememberPartitioner:Boolean
      *   是指是否记录父RDD的分区信息
      */
    val tups: DStream[(String, Int)] = logs.flatMap(_.split(" ").map((_,1)))
    val res: DStream[(String, Int)] =
      tups.updateStateByKey(func,new HashPartitioner(ssc.sparkContext.defaultParallelism),true)

    res.print()

    ssc.start()
    ssc.awaitTermination()


  }
  //(Iterator[(K, Seq[V], Option[S])]) => Iterator[(K, S)]
  val func=(it: Iterator[(String,Seq[Int],Option[Int])])=>{
    it.map( tup => {
      (tup._1, tup._2.sum+tup._3.getOrElse(0))
    })
  }

}
