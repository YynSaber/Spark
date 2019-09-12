package com.yyn.test_1.day12

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  *
  * 统计每个部门中所有大于20岁的不同性别的员工的平均薪资和平均年龄
  *   1. 统计年龄20岁以上的员工
  *   2. 根据部门和性别分组
  *   3. 统计平均薪资和平均年龄
  */
object DepAvgSalaryAndAgeDemo {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._

    //获取数据
    val employee: DataFrame = spark.read.json("dir/employee.json")
    val department: DataFrame = spark.read.json("dir/department.json")

    //统计
    val res: DataFrame = employee
      .filter("age>20")
      .join(department, $"depId" === $"id")
      .groupBy(department("id"), employee("gender"))
      .agg(avg(employee("salary")), avg(employee("age")))

    res.show()

    spark.stop()

  }

}
