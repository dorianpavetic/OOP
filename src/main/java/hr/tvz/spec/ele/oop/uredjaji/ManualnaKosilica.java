package hr.tvz.spec.ele.oop.uredjaji;

import java.math.BigDecimal;

public class ManualnaKosilica extends Uredjaj {

    private BigDecimal masa;

    public ManualnaKosilica(BigDecimal masa, String naziv, BigDecimal cijena) {
        super(naziv, cijena);
        this.masa = masa;
    }

    public BigDecimal getMasa() {
        return masa;
    }

    public void setMasa(BigDecimal masa) {
        this.masa = masa;
    }

    @Override
    public void recikliraj() {
        this.masa = BigDecimal.ZERO;
        System.out.println("Kosilica " + getNaziv() + " je reciklirana u metalno skladište.");
    }
}
