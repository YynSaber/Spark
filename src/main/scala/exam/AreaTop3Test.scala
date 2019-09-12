package exam

import java.util
import java.util.Properties

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{DataTypes, StringType, StructField}
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 需求：按区域统计top3热门商品
  * 实现思路：
  *   1. 创建user_visit_action、product_info表到当前SparkSession并加载数据
  *   2. 从表user_visit_action中获取基础数据：用户点击流日志
  *     获取的字段有：city_id、click_product_id
  *     获取条件：日期范围为：startDate="2019-08-15"，endDate="2019-08-15"
  *              click_product_id（用户点击商品id字段）限定为不为空
  *   3. 获取mysql的city_info表的城市信息
  *   4. 将点击流日志和城市信息进行join，生成临时表tmp_click_product_basic，字段有：cityId, cityName, area, click_product_id
  *   5. 根据表tmp_click_product_basic，统计各区域商品点击次数并生成临时表tmp_area_product_click_count，字段有：area,product_id,click_count,city_infos
  *     city_infos的统计要求：
  *       因为每个区域有很多城市，需要将各个区域涉及到的城市信息拼接起来，比如华南区有广州和三亚，拼接后的city_infos为："4:三亚,3:广州"，其中数字4和3为city_id
  *       此时需要用到GroupConcatDistinctUDAF函数
  *   6. 将各区域商品点击次数临时表tmp_area_product_click_count的product_id字段去关联商品信息表(product_info)的product_id
  *     product_info表的extend_info字段值为json串，需要特殊处理："0"和"1"分别代表了自营和第三方商品
  *     需要用GetJsonObjectUDF函数是从json串中获取指定字段的值，如果product_status为0，返回值为"自营"，如果为1，返回值为"第三方"
  *     生成临时表tmp_area_fullprod_click_count的字段有：
  *     area,product_id,click_count,city_infos,product_name,product_status
  *   7. 将tmp_area_fullprod_click_count进行统计每个区域的top3热门商品（使用开窗函数进行子查询）
  *      统计过程中的外层查询需要增加area_level字段，即按照区域进行分级：
  *      区域有：华北、华东、华南、华中、西北、西南、东北
  *         A级：华北、华东
  *         B级：华南、华中
  *         C级：西北、西南
  *         D级：东北
  *      得到的结果字段有：area、area_level、product_id、city_infos、click_count、product_name、product_status
  *   8. 将结果保存到mysql的area_top3_product表中
  *
  */
object AreaTop3Test {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName(this.getClass.getName)
      .setMaster("local[2]")
    val sc = new SparkContext(conf)

    val spark = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    // 指定获取数据的开始时间和结束时间
    val startDate = "2019-08-15"
    val endDate = "2019-08-15"

    val user_visit_action: RDD[String] = sc.textFile("dir/user_visit_action.txt")
    val product_info: RDD[String] = sc.textFile("dir/product_info.txt")


//    // 创建表user_visit_action并加载数据
//    spark.sql("CREATE TABLE IF NOT EXISTS user_visit_action (session_date string, user_id int, session_id string, page_id int, action_time string, search_keyword string, click_category_id int, click_product_id int, order_category_ids string, order_product_ids string, pay_category_ids string, pay_product_ids string, city_id int)")
//    spark.sql("LOAD DATA LOCAL INPATH  'dir/user_visit_action.txt' INTO TABLE user_visit_action")
//    // 创建表product_info并加载数据
//    spark.sql("CREATE TABLE IF NOT EXISTS product_info (product_id int, product_name string, extend_info string)")
//    spark.sql("LOAD DATA LOCAL INPATH  'dir/product_info.txt' INTO TABLE product_info")

    val uva: RDD[(String, String, String)] = user_visit_action.map(x => {
      val s = x.split("\\u0001")
      (s(0), s(12), s(7))
    })

    val pi: RDD[(Int, String, String)] = product_info.map(x => {
      val s = x.split("\\u0001")
      (s(0).toInt, s(1), s(2))
    })


//    println(uva.collect.toBuffer)

    import spark.implicits._
    val uvadf: DataFrame = uva.toDF("session_date","city_id","click_product_id")

    val pidf: DataFrame = pi.toDF("product_id","product_name","extend_info")

    uvadf.createOrReplaceTempView("user_tmp")

    pidf.createOrReplaceTempView("product_tmp")

    val user_tmp: DataFrame = spark.sql("select city_id , click_product_id from user_tmp where session_date >= '"+startDate+"' and session_date <= '"+endDate+"' and click_product_id <> 'null'")

    user_tmp.createOrReplaceTempView("user_tmp_2")

    val prop = new Properties()
    prop.put("user", "root")
    prop.put("password", "123456")
    val url = "jdbc:mysql://localhost:3306/test"
    val city_info: DataFrame = spark.read.jdbc(url,"city_info",prop)

//    spark.sql("select * from user_tmp_2").show()

    city_info.createOrReplaceTempView("city_tmp")

//    spark.sql(" select * from city_tmp").show()
    val tmp_click_product_basic = spark.sql("select c.city_id,c.city_name,c.area,u.click_product_id from user_tmp_2 u " +
      "left join city_tmp c on c.city_id = u.city_id")

    tmp_click_product_basic.createOrReplaceTempView("tmp_click_product_basic")

    spark.udf.register("GroupConcatDistinct",new GroupConcatDistinctUDAF)

    spark.sql("select area, click_product_id as product_id, count(1) click_count, GroupConcatDistinct(concat(city_id, ':', city_name)) city_infos from tmp_click_product_basic group by area, click_product_id").createOrReplaceTempView("tmp_area_product_click_count")

    spark.udf.register("getjson", new GetJsonObjectUDF, StringType)

    spark.sql("select area, a.product_id product_id, click_count, city_infos, product_name, " +
      "(case getjson(extend_info, 'product_status') when '1' then '第三方' when '0' then '自营' end) product_status " +
      "from tmp_area_product_click_count a join product_tmp p on a.product_id = p.product_id")
      .createOrReplaceTempView("tmp_area_fullprod_click_count")

    val area_top3_product: DataFrame = spark.sql("select area, " +
      "(case when area='华北' or area='华东' then 'A级' when area='华南' or area='华中' then 'B级' when area='西南' or area='西北' then 'C级' when area='东北' then 'D级' end) area_level, " +
      "product_id, city_infos, click_count, product_name, product_status from " +
      "(select area, product_id, city_infos, click_count, product_name, product_status, " +
      "row_number() over(partition by area sort by click_count desc) rank " +
      "from tmp_area_fullprod_click_count) t " +
      "where rank <= 3")

//    area_top3_product.show()
    area_top3_product.write.mode(SaveMode.Append).jdbc(url, "area_top3_product",prop)



    spark.stop()
  }


}
