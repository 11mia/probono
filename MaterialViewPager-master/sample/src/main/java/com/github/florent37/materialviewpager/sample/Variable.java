package com.github.florent37.materialviewpager.sample;

import android.app.Application;

public class Variable extends Application {

    private String name;
    private String username;
    private int age;
    private String aduino_id;
    public String sex;
    public String area;
    @Override
    public void onCreate() {
        //전역 변수 초기화
        age = 0;
        name=username=aduino_id=sex="";
        area = "서울";
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setAge(int age){
        this.age = age;
    }
    public int getAge(){
        return this.age;
    }
    public void setAduino_id(String aduino_id){
        this.aduino_id = aduino_id;
    }
    public String getAduino_id(){
        return this.aduino_id;
    }
    public void setSex(String sex){
        this.sex = sex;
    }
    public void setArea(String area){
        this.area = area;
    }
    public String getSex(){
        return this.sex;
    }
    public String getArea(){
        return this.area;
    }
}