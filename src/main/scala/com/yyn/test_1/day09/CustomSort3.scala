package com.yyn.test_1.day09


  import org.apache.spark.rdd.RDD
  import org.apache.spark.{SparkConf, SparkContext}

  /**
    * @title: CustomSor
    * @projectName test_1
    * @author YongningY
    * @date 2019/8/2 9:42
    */

  //自定义类型只是起到一个封装值得作用 可以不实现Ordered特质
  //隐式
  object CustomSort3 {

    def main(args: Array[String]): Unit = {
      val conf = new SparkConf().setAppName(this.getClass.getName).setMaster("local[2]")
      val sc = new SparkContext(conf)

      val userInfo: RDD[String] = sc.parallelize(Array("mi 25 85","bing 30 85","yuan 18 90"))
      val personRdd: RDD[(String, Int, Int)] = userInfo.map(x => {
        val arr = x.split(" ")
        val name = arr(0)
        val age = arr(1).toInt
        val fv = arr(2).toInt

        (name, age, fv)

      })

      //4 隐式方法
      implicit  def orderedMethod(p:Person): Ordered[Person]= new Ordered[Person] {
        override def compare(that:Person): Int = {
          if(p.fv!=that.fv){
            that.fv-p.fv  //降
          }else{
            p.age-that.age //升
          }
        }
      }

      //3 隐式函数 用implicit修饰 带有一个参数的函数
      implicit val orderedFunc = (p:Person) => new Ordered[Person]{
        override def compare(that: Person): Int = {
          if(p.fv!=that.fv){
            that.fv-p.fv  //降
          }else{
            p.age-that.age //升
          }
        }
      }

      //2 隐式值
      implicit  val ovdVar:Ordering[Person]=new Ordering[Person]{
        override def compare(x: Person,y:Person): Int = {
          if(x.fv!=y.fv){
            y.fv-x.fv  //降
          }else{
            x.age-y.age //升
          }
        }
      }

      //1 隐式Object
      implicit object ordObject extends Ordering[Person]{
        override def compare(x: Person,y:Person): Int ={
          if(x.fv!=y.fv){
            y.fv-x.fv  //降
          }else{
            x.age-y.age //升
          }
        }
      }

      //排序
      val sorted: RDD[(String, Int, Int)] = personRdd.sortBy(x=>Person(x._1,x._2,x._3))

      println(sorted.collect.toBuffer)

      sc.stop()
    }

  }
//case class  Person(val name:String,val age :Int, val fv:Int)
//  extends Ordered[Person] {
//
//  override def toString : String = s"$name,$age,$fv"
//
//  override def compare(that:Person)={
//    if(this.fv!=that.fv){
//      that.fv-this.fv  //降
//    }else{
//      this.age-that.age
//    }
//  }
//
//}