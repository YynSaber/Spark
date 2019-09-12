package com.yyn.test_1.day14

import java.util
import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}



/**
  * @title: KafkaConsumer
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/9 14:58
  */
object KafkaConsumerTest {

  def main(args: Array[String]): Unit = {

    //配置信息
    val prop = new Properties()
    //指定请求的kafka集群列表
    prop.put("bootstrap.servers","hadoop01:9092,hadoop02:9092,hadoop03:9092")
    //指定消费组
    prop.put("group.id","group01")
    //指定消费位置  earliest/latest/none
    //从头消费
    prop.put("auto.offset.reset","earliest")
    //指定消费的key的反序列化方式
    prop.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
//        StringD反eserializer
    //指定value的序列化方式
    prop.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer")

    //得到Consumer实例
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String,String](prop)

    //
    //  首先需要订阅topic
    consumer.subscribe(Collections.singletonList("test"))
    // 开始消费数据
    while(true){
      val msg: ConsumerRecords[String, String] = consumer.poll(1000)
      val it: util.Iterator[ConsumerRecord[String, String]] = msg.iterator()
      while(it.hasNext){
        val msg = it.next()
        println(s"partition: ${msg.partition()}, offset: ${msg.offset()}, key: ${msg.key()} ,value: ${msg.value()}")
      }



    }

  }

}
