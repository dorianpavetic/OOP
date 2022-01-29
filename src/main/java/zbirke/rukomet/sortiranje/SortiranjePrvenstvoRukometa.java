package zbirke.rukomet.sortiranje;

import zbirke.rukomet.Reprezentacija;

import java.util.Comparator;

public class SortiranjePrvenstvoRukometa implements Comparator<Reprezentacija> {
    @Override
    public int compare(Reprezentacija r1, Reprezentacija r2) {
        if(r1.getBrojPobjeda() > r2.getBrojPobjeda()) {
            return -1;
        }
        else if(r1.getBrojPobjeda() < r2.getBrojPobjeda()) {
            return 1;
        }
        else {
            if(r1.getGolRazlika() > r2.getGolRazlika()) {
                return -1;
            }
            else if(r1.getGolRazlika() < r2.getGolRazlika()) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }
}
