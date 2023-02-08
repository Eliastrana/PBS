package edu.ntnu.idatt1002;

import java.time.LocalTime;
public class timeofdaychecker {


    static LocalTime time = LocalTime.now();
    static int hour = time.getHour();


    public static String timeofdaychecker(){
        if (hour >= 0 && hour < 12) {
            return "Good morning";
        } else if (hour >= 12 && hour < 18) {
            return "Good afternoon";
        } else if (hour >= 18 && hour < 24) {
            return "Good evening";
        }
        return null;
    }
}
