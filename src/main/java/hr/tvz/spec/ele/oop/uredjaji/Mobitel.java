package hr.tvz.spec.ele.oop.uredjaji;

import java.math.BigDecimal;

public class Mobitel extends Uredjaj implements Elektricno {
    private String proizvodjac;
    private boolean ukljucen;

    public Mobitel(String proizvodjac, String naziv, BigDecimal cijena) {
        super(naziv, cijena);
        this.proizvodjac = proizvodjac;
        this.ukljucen = false;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public boolean isUkljucen() {
        return ukljucen;
    }

    public void setUkljucen(boolean ukljucen) {
        this.ukljucen = ukljucen;
    }

    @Override
    public void recikliraj() {
        setCijena(BigDecimal.ZERO);
        System.out.println("Mobitel " + getNaziv() + " je uspješno recikliran.");
    }

    @Override
    public void ukljuci() {
        this.ukljucen = true;
    }

    @Override
    public void iskljuci() {
        this.ukljucen = false;
    }

    public void uspostaviPozivNaBroj(String brojKojiSeZove) {
        if(ukljucen)
            System.out.println("Uspostava poziva na broj " + brojKojiSeZove);
        else
            System.out.println("Potrebno je uključiti mobitel da bi uspostavili poziv");
    }
}
