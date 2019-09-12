package com.yyn.test_1.day13

import org.apache.spark.sql.SparkSession

/**
  * @title: UDAFDemo
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/7 9:45
  */
object UDAFDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    //注册UDAF
//    spark.udf.register("myaverage",new MyAverge2)

    val df = spark.read.json("dir/employee.json")

    df.createOrReplaceTempView("t_employee")

    val res=spark.sql("select myaverage(age) from t_employee")

    res.show()

    spark.stop()
  }

}
