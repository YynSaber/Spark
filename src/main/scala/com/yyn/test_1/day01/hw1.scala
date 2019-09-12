package com.yyn.test_1.day01

import java.text.SimpleDateFormat

import scala.io.StdIn

object hw1 {
  def main(args:Array[String]):Unit = {
//    sort1(4,3)
//    sort2(2,6,12)
//    run(1900)
//    time(21,19,59)
//    flower_1000()
//    print(rob(4))
//    d()
      sort2_2()
  }

  def sort1(x:Int,y:Int){
    if(x>y){
      println(s"$y $x")
    }
    else{
      println(s"$x $y")
    }
  }

  def sort2(x:Int,y:Int,z:Int){
    if(x>y){
      if(y>z){
        println(s"$z $y $x")
      }
      else{ //z>y
        if(x>z)
          println(s"$y $z $x")
        else {
          println(s"$y $x $z")
        }
      }
    }
    else{  //y>x
      if(x>z){
        println(s"$z $x $y")
      }
      else{ //y>x && z>x
        if(y>z){
          println(s"$x $z $y")
        }
        else{
          println(s"$x $y $z")
        }
      }
    }
  }

  def sort2_2(): Unit ={
    var a = StdIn.readInt()
    var b = StdIn.readInt()
    var c = StdIn.readInt()
    //3,2,1
    var  tmp = 0 //中间变量
    if(a>c){
      //交换a和c的值
      tmp = a
      a = c
      c = tmp
    }
    //因为上面数是有序的所以可以直接叫a和c,但是能保证b不是最小
    if(a>b){
      tmp = a
      a = b
      b = tmp
    }
    if(b>c){
      tmp = b
      b = c
      c = tmp
    }
    println(a+" "+b+" "+c)
  }

  def d(): Unit ={
    var h = 23
    var m = 59
    var s = 59
    //秒自增
    s += 1
    if(s == 60){
      s = 0
      m+=1
      if(m == 60){
        m = 0
        h += 1
        if(h == 24){
          h = 0
        }
      }
    }
    println(h+":"+m+":"+s)
  }

  def run(year:Int): Unit = {
    if (year % 4 == 0 || year % 100 != 0 && year % 400 == 0) {
      println(s"$year is 闰年")
    }
    else{
      println(s"$year is 平年")
    }
  }

  def time(hour:Int,minute:Int,second:Int): Unit ={

    var h=hour
    var m=minute
    var s=second
    if(s<59){
      s+=1
    }
    if(s==59){
      s=0
      m+=1
      if(m==60){
        m=0
        h+=1
        if(h==24){
          h=0
        }
      }
    }

    if(s<10 && m >=10 && h>=10){
      val ss = s.toString()
      println(s"$h:$m:0$s")
    }
    if(m<10 && s >=10 && h>=10){
      val mm = m.toString()
      println(s"$h:0$m:$s")
    }
    if(h<10 && m >=10 && s>=10){
      val m = s.toString()
      println(s"0$h:$m:$s")
    }

    println(s"$h:$m:$s")
  }
  def flower_1000(): Unit ={

    for(i <- 100 to 999){
      var g = i%10

      var b = i/10/10%10

      var s = i/10%10


      if(b*b*b+s*s*s+g*g*g==i){
        println(i)
      }
    }
  }
  def rob(n:Int): Int ={
    if(n==1 || n==2) 1
    else{
      rob(n-1)+rob(n-2)
    }
  }

}

