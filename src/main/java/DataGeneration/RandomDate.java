package model;
import java.util.GregorianCalendar;
public class RandomDate {
    public static GregorianCalendar randomDate(){
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(1999, 2023);

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return gc;
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}

