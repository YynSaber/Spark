package com.yyn.test_1.day11

import java.sql.{Connection, Date, DriverManager, PreparedStatement}

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @title: IpSearchDemo
  * @author YongningY
  *
  * 需求:根据用户访问的ip地址来统计所属区域并统计访问量
  * 思路:1. 获取ip的基本数据
  *     2. 把ip基础数据广播出去
  *     3. 获取用户访问数据
  *     4. 通过ip基础信息来判断用户数据那个区域
  *     5. 通过得到的区域区域来统计访问量
  *     6. 输出结果
  */
object IpSearchDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("this.getClass.getName").setMaster("local[2]")
    //2.创建SparkContext 提交SparkApp的入口
    val sc = new SparkContext(conf)

    //获取Ip地址数据
    val ipInfo = sc.textFile("H://idea/ip.txt")
      .map(line => {
        val fields = line.split("\\|")
        val startIP = fields(2) // 其实ip
        val endIP = fields(3) // 结束ip
        val province = fields(6) // ip段对应的省份
        (startIP, endIP, province)
      })

    // 将Ip段的基础数据进行广播
    // 成为一个job
    val broadcastIpInfo: Broadcast[Array[(String, String, String)]] =
      sc.broadcast(ipInfo.collect)

    println(ipInfo.collect.toBuffer)

    println("----------")

    //获取用户点击六日志 并切分
    val logs = sc.textFile("H://idea/http.log")
      .map(line => {
        val userIp = line.split("\\|")(1) //用户IP
        val userIp_Long = ip2Long(userIp)  //long类型用户IP
        val ipInfoArr = broadcastIpInfo.value
        //通过二分查找 找到用户ip对应的ip段的下标
        val index = binarySearch(ipInfoArr,userIp_Long)
        val province = ipInfoArr(index)._3

        (province,1)
      })

    println(logs.collect.toBuffer)

    println("---------")

    //统计省份对应的访问量

    val aggr: RDD[(String, Int)] = logs.reduceByKey(_+_)
    println(aggr.collect.toBuffer)

  }

  /**
    * 把ip转换为long类型 直接给 125.125.124.2
    * @param ip
    * @return
    */
  def ip2Long(ip: String): Long = {
    val fragments: Array[String] = ip.split("[.]")
    var ipNum = 0L
    for (i <- 0 until fragments.length) {
      //| 按位或 只要对应的二个二进位有一个为1时，结果位就为1 <<左位移
      ipNum = fragments(i).toLong | ipNum << 8L
    }
    ipNum
  }

  def binarySearch(arr:Array[(String, String, String)],ip:Long):Int={
    //开始和结束值
    var start = 0
    var end = arr.length-1
    while(start <= end){
      //求中间值
      val middle = (start+end)/2
      //arr(middle)获取数据中的元组\
      //元组存储着ip开始 ip结束 省份
      //因为需要判断时候在ip的范围之内.,所以需要取出元组中的值
      //若这个条件满足就说明已经找到了ip
      if((ip >= arr(middle)._1.toLong) &&(ip<=arr(middle)._2.toLong)){
        return middle
      } else if(ip < arr(middle)._1.toLong){
        end = middle -1
      } else{
        start = middle+1
      }
    }
    -1
  }

  // 写入数据到mysql的函数
  val data2MySQL = (it: Iterator[(String, Int)]) => {
    var conn: Connection = null;
    var ps: PreparedStatement = null;

    val sql = "insert into location_info(location, counts, access_date) values(?,?,?)"

    val jdbcUrl = "jdbc:mysql://hadoop01:3306/test?useUnicode=true&characterEncoding=utf8"
    val user = "root"
    val password = "root"

    try {
      conn = DriverManager.getConnection(jdbcUrl, user, password)
      it.foreach(tup => {
        ps = conn.prepareStatement(sql)
        ps.setString(1, tup._1)
        ps.setInt(2, tup._2)
        ps.setDate(3, new Date(System.currentTimeMillis()))
        ps.executeUpdate()
      })
    } catch {
      case e: Exception => println(e.printStackTrace())
    } finally {
      if (ps != null)
        ps.close()
      if (conn != null)
        conn.close()
    }
  }

}


