package DataGeneration;
import java.util.GregorianCalendar;

/**
 * Class used to generate random dates for use in the Database.
 */
public class RandomDate {
    public static GregorianCalendar randomDate(){
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(2020, 2023);

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, 212);

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return gc;
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}

