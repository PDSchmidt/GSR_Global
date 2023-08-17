package model;

import java.time.LocalDate;

/**
 * Class used to generate and hold the LocalDate of the user when they generate a new order.
 * @author Paul Schmidt
 */
public class AppCalender {
    /**
     * The Date
     */
    LocalDate date;

    /**
     * Constructs an AppCalendar and sets the Date to Today's current date.
     */
    public AppCalender() {
        this.date = LocalDate.now();

    }

    /**
     * Returns a formatted version of the Date.
     * @return a formatted version of the Date.
     */
    public String getFormatted() {
        return date.toString();
    }
}
