package com.com_4dmacau.parkinglog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tonylam on 28/04/2016.
 */
public class Record_Text{
    private int id;
    private String time;
    private String floor;
    private String space_num;
    private String area;
    private String direction_from_lift;

    private int[] direction_from_lift_int_array = {1,2,3,4,6,7,8,9};
    private String[] direction_from_lift_string_array = {"0","1","2","3","4","5","6","7"};
    private int[] direction_from_lift_icon_id_array = {R.drawable.direction_1_icon, R.drawable.direction_2_icon, R.drawable.direction_3_icon, R.drawable.direction_4_icon, R.drawable.direction_6_icon, R.drawable.direction_7_icon, R.drawable.direction_8_icon, R.drawable.direction_9_icon};

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

        output_txt += time + " Parked at";

        if ( !floor.equals("") )
        {
            output_txt += floor + " Floor";
        }
        if ( !area.equals("") )
        {
            output_txt += "at Area " + area ;
        }
        if ( !space_num.equals("") )
        {
            output_txt += " in Space " + space_num;
        }
        if ( !direction_from_lift.equals("") )
        {
            output_txt += ", go " + direction_from_lift + "after elevator";
        }

        return output_txt + "。\n";
    }

    public int getFloorIconId(){
        if ( floor != null && !floor.equals("") )
        {
            switch (Integer.parseInt(floor))
            {
                case -6:
                    return R.drawable.num_n6_icon;
                case -5:
                    return R.drawable.num_n5_icon;
                case -4:
                    return R.drawable.num_n4_icon;
                case -3:
                    return R.drawable.num_n3_icon;
                case -2:
                    return R.drawable.num_n2_icon;
                case -1:
                    return R.drawable.num_n1_icon;
                case 0:
                    return R.drawable.num_0_icon;
                case 1:
                    return R.drawable.num_1_icon;
                case 2:
                    return R.drawable.num_2_icon;
                case 3:
                    return R.drawable.num_3_icon;
                case 4:
                    return R.drawable.num_4_icon;
                case 5:
                    return R.drawable.num_5_icon;
                case 6:
                    return R.drawable.num_6_icon;
                case 7:
                    return R.drawable.num_7_icon;
                case 8:
                    return R.drawable.num_8_icon;
                case 9:
                    return R.drawable.num_9_icon;
                default:
                    return 0;
            }
        }
        else
        {
            return 0;
        }
    }

    public int getDirectionIconId(){
        if ( direction_from_lift != null && !direction_from_lift.equals("") )
        {
            for ( int i = 0; i < direction_from_lift_string_array.length; i++ ){
                if ( direction_from_lift.equals(direction_from_lift_string_array[i]) )
                {
                    return direction_from_lift_icon_id_array[i];
                }
            }
            return 0;
        }
        else
        {
            return 0;
        }
    }

}
