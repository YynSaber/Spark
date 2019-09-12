package com.yyn.test_1.day13

import java.util.Properties

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * @title: LoadDataForJdbc
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/8 10:50
  */
object LoadDataForJdbc {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val prop = new Properties()
    prop.put("user","root")
    prop.put("password","123456")
//    prop.setProperty("driver", "com.mysql.jdbc.Driver")

    val url = "jdbc:mysql://localhost:3306/test1"

    val df = spark.read.jdbc(url,"student",prop)

    df.show()

//    val jdbcDF2 = spark.read.format("jdbc")
//      .option("url","jdbc:mysql://localhost:3306/test1")
//      .option("dbtable","student")
//      .option("user","root")
//      .option("password","123456").load()
//    jdbcDF2.show()



    spark.stop()

  }

}
