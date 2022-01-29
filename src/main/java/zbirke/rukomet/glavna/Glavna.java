package zbirke.rukomet.glavna;

import zbirke.rukomet.Drzava;
import zbirke.rukomet.Reprezentacija;
import zbirke.rukomet.sortiranje.SortiranjePoBrojuPobjeda;
import zbirke.rukomet.sortiranje.SortiranjePrvenstvoRukometa;

import java.util.*;

public class Glavna {
    public static void main(String[] args) {
        Reprezentacija prvaRepka = new Reprezentacija(Drzava.HRVATSKA, 10, 2, 45);
        Reprezentacija drugaRepka = new Reprezentacija(Drzava.FRANCUSKA, 13, 1, 62);
        Reprezentacija trecaRepka = new Reprezentacija(Drzava.SRBIJA, 7, 0, 17);
        Reprezentacija cetvrtaRepka = new Reprezentacija(Drzava.DANSKA, 13, 1, 52);
        Reprezentacija petaRepka = new Reprezentacija(Drzava.NIZOZEMSKA, 10, 1, 51);

        //LIST
        List<Reprezentacija> listaReprezentacija = Arrays.asList(prvaRepka, drugaRepka,
                trecaRepka, cetvrtaRepka, cetvrtaRepka, petaRepka);

        /*for (int i = 0; i < listaReprezentacija.size(); i++) {
            System.out.println(listaReprezentacija.get(i));
        }

        for (Reprezentacija repka : listaReprezentacija) {
            System.out.println(repka);
        }

        Iterator<Reprezentacija> iterator = listaReprezentacija.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        listaReprezentacija
                .stream()
                .forEach(System.out::println);



        Collections.sort(listaReprezentacija, new SortiranjePrvenstvoRukometa());

        for (Reprezentacija repka : listaReprezentacija) {
            System.out.println(repka);
        } */

        //SET
        /*SortedSet<Reprezentacija> setReprezentacija = new TreeSet<>(new SortiranjePrvenstvoRukometa());
        setReprezentacija.add(prvaRepka);
        setReprezentacija.add(drugaRepka);
        setReprezentacija.add(trecaRepka);
        setReprezentacija.add(cetvrtaRepka);
        setReprezentacija.add(cetvrtaRepka);

        for (Reprezentacija repka : setReprezentacija) {
            System.out.println(repka);
        } */

        //MAP
        Map<Integer, Set<Reprezentacija>> grupiranjeRepkiPoPobjedamaMap = new HashMap<>();

        for (Reprezentacija repka:  listaReprezentacija) {
            if(grupiranjeRepkiPoPobjedamaMap.containsKey(repka.getBrojPobjeda())) {
                Set<Reprezentacija> reprezentacijeSTrazenimBrojemPobjedama =
                        grupiranjeRepkiPoPobjedamaMap.get(repka.getBrojPobjeda());
                reprezentacijeSTrazenimBrojemPobjedama.add(repka);
                grupiranjeRepkiPoPobjedamaMap.put(repka.getBrojPobjeda(), reprezentacijeSTrazenimBrojemPobjedama);
            }
            else {
                Set<Reprezentacija> reprezentacijeSTrazenimBrojemPobjedama = new HashSet<>();
                reprezentacijeSTrazenimBrojemPobjedama.add(repka);
                grupiranjeRepkiPoPobjedamaMap.put(repka.getBrojPobjeda(), reprezentacijeSTrazenimBrojemPobjedama);
            }
        }

        Set<Integer> nesortiraniSet = grupiranjeRepkiPoPobjedamaMap.keySet();
        SortedSet<Integer> sortiraniSetBrojaPobjeda = new TreeSet<>(new SortiranjePoBrojuPobjeda());
        sortiraniSetBrojaPobjeda.addAll(nesortiraniSet);


        for (Integer brojPobjeda : sortiraniSetBrojaPobjeda) {
            System.out.println("Broj pobjeda: " + brojPobjeda);
            Set<Reprezentacija> listaRepkiSTimBrojemPobjeda = grupiranjeRepkiPoPobjedamaMap.get(brojPobjeda);
            for(Reprezentacija reprezentacija : listaRepkiSTimBrojemPobjeda) {
                System.out.println("\t- " + reprezentacija);
            }
        }
    }
}
