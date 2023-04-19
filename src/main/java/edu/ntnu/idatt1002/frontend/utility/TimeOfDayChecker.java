package edu.ntnu.idatt1002.frontend.utility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A class that checks the time of day.
 */
public class TimeOfDayChecker {


    /**
     * The current time.
     */
    static LocalTime time = LocalTime.now();
    /**
     * The current hour.
     */
    static int hour = time.getHour();


    /**
     * Returns a string that says good morning, good afternoon or good evening.
     *
     * @return the string that says good morning, good afternoon or good evening
     */
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

    /**
     * Returns a string that says good morning, good afternoon or good evening.
     *
     * @return the string that says good morning, good afternoon or good evening
     */
    @Override
    public String toString() {
        return timeofdaychecker();
    }

    /**
     * Returns the selected month from the DatePicker control.
     *
     * @param datePicker the date picker
     * @return the selected month from the DatePicker control
     */
    public static String getSelectedMonth(String datePicker) {
        LocalDate selectedDate = LocalDate.parse(datePicker); // Get the selected date from the DatePicker control
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM"); // Format the month as a string
        return selectedDate.format(monthFormatter); // Return the month as a string
    }

    /**
     * Returns the current month.
     *
     * @return the current month
     */
    public static String getCurrentMonth() {
        LocalDate currentDate = LocalDate.now(); // Get the current date
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM"); // Format the month as a string
        return currentDate.format(monthFormatter); // Return the month as a string
    }

    /**
     * Returns the previous month.
     *
     * @return the previous month
     */
    public static String getPreviousMonth() {
        LocalDate currentDate = LocalDate.now().minusMonths(1);
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM"); // Format the month as a string
        return currentDate.format(monthFormatter); // Return the month as a string
    }

    /**
     * Returns the current year.
     *
     * @return the current year
     */
    public static int getYear(){
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        return year;
    }
}
