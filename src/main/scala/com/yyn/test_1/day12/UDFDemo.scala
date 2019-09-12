package com.yyn.test_1.day12

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StringType

/**
  * @title: UDFDemo
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/6 16:33
  */
object UDFDemo {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val df = spark.read.json("dir/people.json")

    //注册UDF函数
//    spark.udf.register("concatname",new ConcatNameUDF,StringType)
    spark.udf.register("concatname",(str:String)=>"name"+str)

    //注册临时表
    df.createOrReplaceTempView("t_people")

    //调用UDF函数查询
    spark.sql("select concatname(name),age from t_people").show()

    spark.stop()

  }
}
