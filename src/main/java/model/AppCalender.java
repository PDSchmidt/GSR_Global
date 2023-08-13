package model;

import java.time.LocalDate;
import java.util.GregorianCalendar;

public class AppCalender {
    LocalDate date;
    public AppCalender() {
        this.date = LocalDate.now();

    }
    public String getFormatted() {
        return date.toString();
    }
}
