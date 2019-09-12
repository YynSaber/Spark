package com.yyn.test

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * @title: Phone3
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/7 8:27
  */
object Phone3 {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    //import spark.implicits._

    //获取数据
    val phone_data: DataFrame = spark.read.json("H://idea/JsonTest02.json")

//    val res: DataFrame = phone_data
//      .filter("status==1")
//      .groupBy("phoneNum")
//      .agg(sum("money"))
//      .sort(sum("money").desc)
//
//    res.show()

    phone_data.createOrReplaceTempView("t_phone")

    val df: DataFrame = spark.sql("select phoneNum,sum(money) sum from t_phone where status = 1 group by phoneNum order by sum desc limit 10")

    val df2: DataFrame = spark.sql("select terminal,count(*) num from t_phone group by terminal order by num desc limit 10")

    val df3 = spark.sql("select province,count(*) count from t_phone group by province order by count desc limit 3")

    df.show()
    df2.show()
    df3.show()
    spark.stop()

    /**
      * val res1: DataFrame = spark.sql("select phoneNum, sum(case status when 1 then money else 0 end) sum_money from tmp group by phoneNum order by sum_money desc")
      *
      * res1.show()
      *
      * // 2. 统计所有系统类型登录总次数并降序排序
      *
      * val res2: DataFrame = spark.sql("select terminal,count(terminal) sum_type from tmp group by terminal order by sum_type desc")
      *
      * //    res2.show()
      *
      * // 3. 统计所有用户在各省登录的次数的Top3
      *
      * val res3: DataFrame = spark.sql("select province,count(province) sum_pro from tmp group by province order by sum_pro desc limit 3"
      */
  }

}
