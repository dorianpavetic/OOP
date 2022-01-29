package zbirke.rukomet;

import java.util.Objects;

public class Reprezentacija {
    private Drzava drzava;
    private Integer brojPobjeda;
    private Integer brojNerijesenih;
    private Integer golRazlika;

    public Reprezentacija(Drzava drzava, Integer brojPobjeda, Integer brojNerijesenih, Integer golRazlika) {
        this.drzava = drzava;
        this.brojPobjeda = brojPobjeda;
        this.brojNerijesenih = brojNerijesenih;
        this.golRazlika = golRazlika;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    public Integer getBrojPobjeda() {
        return brojPobjeda;
    }

    public void setBrojPobjeda(Integer brojPobjeda) {
        this.brojPobjeda = brojPobjeda;
    }

    public Integer getBrojNerijesenih() {
        return brojNerijesenih;
    }

    public void setBrojNerijesenih(Integer brojNerijesenih) {
        this.brojNerijesenih = brojNerijesenih;
    }

    public Integer getGolRazlika() {
        return golRazlika;
    }

    public void setGolRazlika(Integer golRazlika) {
        this.golRazlika = golRazlika;
    }

    @Override
    public String toString() {
        return "Reprezentacija{" +
                "drzava=" + drzava +
                ", brojPobjeda=" + brojPobjeda +
                ", brojNerijesenih=" + brojNerijesenih +
                ", golRazlika=" + golRazlika +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reprezentacija that = (Reprezentacija) o;
        return drzava == that.drzava && Objects.equals(brojPobjeda, that.brojPobjeda) && Objects.equals(brojNerijesenih, that.brojNerijesenih) && Objects.equals(golRazlika, that.golRazlika);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drzava, brojPobjeda, brojNerijesenih, golRazlika);
    }
}
