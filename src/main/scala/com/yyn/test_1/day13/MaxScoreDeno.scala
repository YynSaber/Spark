package com.yyn.test_1.day13

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * 用sparksql开窗函数实现需求
  *   每个班最高成绩的学生信息
  */
object MaxScoreDeno {

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    val df: DataFrame = spark.read.json("H://idea/Score.json")

    df.createOrReplaceTempView("t_score")

//    val res: DataFrame = spark.sql("select class, max(score) maxscore from t_score group by class ")
    val res: DataFrame = spark.sql("select * " +
      "from (" +
      "select name,class,score, row_number() over(partition by class order by score desc) rk from t_score)t " +
      "where rk = 1 ")



    res.show()



    spark.stop()




  }

}
