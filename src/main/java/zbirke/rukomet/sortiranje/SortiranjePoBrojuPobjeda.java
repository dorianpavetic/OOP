package zbirke.rukomet.sortiranje;

import java.util.Comparator;

public class SortiranjePoBrojuPobjeda implements Comparator<Integer> {
    @Override
    public int compare(Integer brojPobjeda1, Integer brojPobjeda2) {
        if(brojPobjeda1 > brojPobjeda2)
            return -1;
        else if(brojPobjeda1 < brojPobjeda2)
            return 1;
        else
            return 0;
    }
}
