package com.a4dmacau.pakwai;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tonylam on 28/04/2016.
 */
public class Record_Text {
    private int id;
    private String time;
    private String floor;
    private String space_num;
    private String area;
    private String direction_from_lift;

    public Record_Text(){}

    public Record_Text(int id, String time, String floor, String space_num, String area, String direction_from_lift)
    {
        this.id = id;
        this.time = time;
        this.floor = floor;
        this.space_num = space_num;
        this.area = area;
        this.direction_from_lift = direction_from_lift;
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

    public String getSpace_num() {
        return space_num;
    }

    public String getDirection_from_lift() {
        return direction_from_lift;
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

    public void setFloor(int floor) {
        this.floor = String.valueOf(floor);
    }

    public void setSpace_num(String space_num) {
        this.space_num = space_num;
    }

    public void setDirection_from_lift(String direction_from_lift) {
        this.direction_from_lift = direction_from_lift;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {

        String output_txt = "";

        output_txt += time + "停於";

        if ( !floor.equals("") )
        {
            output_txt += floor + "樓";
        }
        if ( !area.equals("") )
        {
            output_txt += area + "區";
        }
        if ( !space_num.equals("") )
        {
            output_txt += space_num + "號車位";
        }
        if ( !direction_from_lift.equals("") )
        {
            output_txt += "，出電梯後向" + direction_from_lift + "方走";
        }

        return output_txt + "。\n";
    }
}
