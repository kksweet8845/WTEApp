package com.example.eatanddrink.utils;

import java.util.ArrayList;
import java.util.Collections;

public class MyStringSorter {

    private ArrayList<MyString> mList = new ArrayList<MyString>();

    public MyStringSorter(ArrayList<MyString> list){
        this.mList = list;
    }

    public ArrayList<MyString> getSortedList() {
        Collections.sort(mList);
        return mList;
    }
}
