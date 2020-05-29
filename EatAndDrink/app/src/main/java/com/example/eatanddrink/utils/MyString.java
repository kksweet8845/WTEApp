package com.example.eatanddrink.utils;

public class MyString implements Comparable<MyString> {

    private String str;

    public MyString(String str){
        this.str = str;
    }


    public String getStr(){
        return this.str;
    }

    public void setStr(String str){
        this.str = str;
    }

    @Override
    public int compareTo(MyString o) {
        return (this.str.length() < o.getStr().length() ? -1 :
                (this.str.length() == o.getStr().length() ? 0 : 1));
    }
}