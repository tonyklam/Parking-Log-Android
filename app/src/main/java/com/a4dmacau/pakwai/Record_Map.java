package com.a4dmacau.pakwai;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tonylam on 28/04/2016.
 */
public class Record_Map {
    private int id;
    private String time;
    private String floor;
    private String pin_x;
    private String pin_y;

    public Record_Map(){}

    public Record_Map(int id, String time, String floor, String pin_x, String pin_y)
    {
        this.id = id;
        this.time = time;
        this.floor = floor;
        this.pin_x = pin_x;
        this.pin_y = pin_y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTime(Date time){
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(time);
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setPin_x(String pin_x) {
        this.pin_x = pin_x;
    }

    public void setPin_x_with_Float(Float f){
        this.pin_x = String.valueOf(f);
    }

    public void setPin_y(String pin_y) {
        this.pin_y = pin_y;
    }

    public void setPin_y_with_Float(Float f){
        this.pin_y = String.valueOf(f);
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getFloor() {
        return floor;
    }

    public int getFloor_as_int(){
        return Integer.parseInt(floor);
    }

    public String getPin_x() {
        return pin_x;
    }

    public Float getPin_x_as_Float(){
        return Float.parseFloat(pin_x);
    }

    public String getPin_y() {
        return pin_y;
    }

    public Float getPin_y_as_Float(){
        return Float.parseFloat(pin_y);
    }

    @Override
    public String toString() {

        String output_txt = "";

        //output_txt += ""

        return output_txt + "。\n";
    }
}
