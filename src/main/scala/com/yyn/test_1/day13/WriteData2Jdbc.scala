package com.yyn.test_1.day13

import java.util.Properties

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * @title: WriteData2Jdbc
  * @projectName test_1
  * @author YongningY
  * @date 2019/8/8 11:49
  */
object WriteData2Jdbc {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val employee = spark.read.json("dir/employee.json")
    val nameAndSalaryDF = employee.select("name","salary")

    val prop = new Properties()
    prop.put("user","root")
    prop.put("password","123456")

    val url = "jdbc:mysql://localhost:3306/test"

    nameAndSalaryDF.write.mode(SaveMode.Append).jdbc(url,"employee",prop)

//    nameAndSalaryDF.write.mode(SaveMode.Append).format("jdbc")
//      .option("url", "jdbc:mysql://localhost:3306/test")
//      .option("dbtable", "employee")
//      .option("user", "root")
//      .option("password", "123456")
//      .save()


    spark.stop()
  }

}
