package hr.tvz.spec.ele.oop.uredjaji;

import java.math.BigDecimal;

public abstract class Uredjaj {
    private String naziv;
    private BigDecimal cijena;

    public Uredjaj(String naziv, BigDecimal cijena) {
        this.naziv = naziv;
        this.cijena = cijena;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public BigDecimal getCijena() {
        return cijena;
    }

    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

    public abstract void recikliraj();

    @Override
    public String toString() {
        return "Uredjaj{" +
                "naziv='" + naziv + '\'' +
                ", cijena=" + cijena +
                '}';
    }
}
