package hr.tvz.spec.ele.oop.glavna;

import hr.tvz.spec.ele.oop.uredjaji.ManualnaKosilica;
import hr.tvz.spec.ele.oop.uredjaji.Mobitel;
import hr.tvz.spec.ele.oop.uredjaji.Uredjaj;

import java.math.BigDecimal;

public class Glavna {

    public static final int BROJ_UREDJAJA = 2;

    public static void main(String[] args) {
        Mobitel noviMob = new Mobitel(
                "Samsung", "Galaxy S50", new BigDecimal(50));
        ManualnaKosilica manualnaKosilica = new ManualnaKosilica(
                BigDecimal.valueOf(20), "Kosilica", new BigDecimal(100));

        Uredjaj[] skladiste = new Uredjaj[BROJ_UREDJAJA];
        skladiste[0] = noviMob;
        skladiste[1] = manualnaKosilica;

        for(Uredjaj uredjaj : skladiste) {
            System.out.println("Na skladištu imamo: ");
            System.out.println(uredjaj);
            if(uredjaj instanceof Mobitel mobitel)
                System.out.println("Proizvodjac: " + mobitel.getProizvodjac());
            else if(uredjaj instanceof ManualnaKosilica kosilica)
                System.out.println("Masa: " + kosilica.getMasa());
        }

        noviMob.ukljuci();
        noviMob.uspostaviPozivNaBroj("555-333-958");
    }
}
