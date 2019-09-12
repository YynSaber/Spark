package com.yyn.test0810

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * @title: Phone
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/10 15:37
  */
object Phone {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val phone_data: DataFrame = spark.read.json("dir/JsonTest02.json")

    phone_data.createOrReplaceTempView("t_phone")

    val df1: DataFrame = spark.sql("select phoneNum , sum(money) sum from t_phone where status = 1 group by phoneNum order by sum desc limit 10")

    val df2 = spark.sql("select terminal,count(*) num from t_phone group by terminal order by num desc limit 10")

    val df3: DataFrame = spark.sql("select phoneNum,province,count from (select phoneNum,province,count,row_number() over(partition by province order by count desc) rk from (select phoneNum,province,count(*) count from t_phone group by province,phoneNum)t1)t2 where rk <=3 ")



    df1.show()
    df2.show()
    df3.show()

    spark.stop()




  }

}
