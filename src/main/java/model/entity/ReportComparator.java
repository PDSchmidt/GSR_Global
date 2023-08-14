package model.entity;

import java.util.Comparator;

public class ReportComparator implements Comparator<String> {

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
