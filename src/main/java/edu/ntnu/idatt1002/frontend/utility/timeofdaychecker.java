package edu.ntnu.idatt1002.frontend.utility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    @Override
    public String toString() {
        return timeofdaychecker();
    }

    public static String getSelectedMonth(String datePicker) {
        LocalDate selectedDate = LocalDate.parse(datePicker); // Get the selected date from the DatePicker control
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM"); // Format the month as a string
        return selectedDate.format(monthFormatter); // Return the month as a string
    }
}
