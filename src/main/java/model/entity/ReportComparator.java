package model.entity;

import java.util.Comparator;

/**
 * A comparator used for sorting collections comprised of Reports via their titles
 * @author Paul Schmidt
 */
public class ReportComparator implements Comparator<String> {

    /**
     * Compares two Report objects via their titles for the sake of sorting in a collection.
     * @param o1 the first report to be compared.
     * @param o2 the second report to be compared.
     * @return the comparison of the lengths if not equal, the string comparison otherwise
     */
    @Override
    public int compare(String o1, String o2) {
        int result = 0;
        if(o1.length() != o2.length()) {
            result = o1.length() - o2.length();
        } else {
            result = o1.compareTo(o2);
        }
        return result;
    }
}
