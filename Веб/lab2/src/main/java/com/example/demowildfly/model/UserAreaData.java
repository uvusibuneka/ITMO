package com.example.demowildfly.model;

import java.util.ArrayList;
import java.util.List;

public class UserAreaData implements java.io.Serializable{
    private final List<AreaData> data = new ArrayList<>();

    public void addData(AreaData data){
        this.data.add(data);
    }
    public List<AreaData> getData(){
        return data;
    }

}
