package model;

import java.util.GregorianCalendar;

public class AppCalender extends GregorianCalendar {
    public String getFormatted() {
        return this.YEAR + "-" +
                this.MONTH + "-" + this.DAY_OF_MONTH;
    }
}
