package com.yyn.test_1.day02;

/**
 * java中 要实现延迟加载 一般会使用单例的懒汉模式去实现
 */

public class JavaLazy {

    private  static MyProperty prop;

    public  static MyProperty getProp(){
        if(prop == null){
            prop = new MyProperty();
        }
        System.out.println("getProp");
        return prop;
    }

    public static void main(String[] args) {
        System.out.println(JavaLazy.getProp());
    }
}

class MyProperty{}
