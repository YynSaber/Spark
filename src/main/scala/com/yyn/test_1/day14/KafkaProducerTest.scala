package com.yyn.test_1.day14

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


/**
  * @title: KafkaProductTest
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/9 14:34
  */
object KafkaProducerTest {

  def main(args: Array[String]): Unit = {

    val prop = new Properties()
    //指定请求的kafka集群列表
    prop.put("bootstrap.servers","hadoop01:9092,hadoop02:9092,hadoop03:9092")
    //制定相应方式
    prop.put("acks","0")
    //请求失败时 重试次数
    prop.put("retries","3")
    //指定key的序列化方式
    prop.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
//    StringSerializer
    //指定value的序列化方式
    prop.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer")

    val producer: KafkaProducer[String, String] = new KafkaProducer[String,String](prop)

    //模拟一些数据并发送给kafka
    for( i <- 1 to 1000){
      val msg = s"${i} : this is kafka data"
      producer.send(new ProducerRecord[String,String]("test",msg))
      Thread.sleep(500)
    }


    producer.close()


  }

}
