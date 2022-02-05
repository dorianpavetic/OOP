package hr.java.production.model;

import hr.java.production.enums.StatusZadatka;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class Zadatak {
    private long brojZadatka;
    private LocalDateTime datumKreiranja;
    private LocalDateTime datumPocetkaIzrade;
    private Set<Item> items;
    private StatusZadatka statusZadatka;

    public Zadatak(long brojZadatka, LocalDateTime datumPocetkaIzrade, Set<Item> items) {
        this.brojZadatka = brojZadatka;
        this.datumKreiranja = LocalDateTime.now();
        this.datumPocetkaIzrade = datumPocetkaIzrade;
        this.items = items;
        this.statusZadatka = StatusZadatka.ZA_IZRADU;
    }

    public long getBrojZadatka() {
        return brojZadatka;
    }

    public void setBrojZadatka(long brojZadatka) {
        this.brojZadatka = brojZadatka;
    }

    public LocalDateTime getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(LocalDateTime datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public LocalDateTime getDatumPocetkaIzrade() {
        return datumPocetkaIzrade;
    }

    public void setDatumPocetkaIzrade(LocalDateTime datumPocetkaIzrade) {
        this.datumPocetkaIzrade = datumPocetkaIzrade;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public StatusZadatka getStatusZadatka() {
        return statusZadatka;
    }

    public void setStatusZadatka(StatusZadatka statusZadatka) {
        this.statusZadatka = statusZadatka;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zadatak zadatak = (Zadatak) o;
        return brojZadatka == zadatak.brojZadatka && Objects.equals(datumKreiranja, zadatak.datumKreiranja) && Objects.equals(datumPocetkaIzrade, zadatak.datumPocetkaIzrade) && Objects.equals(items, zadatak.items) && statusZadatka == zadatak.statusZadatka;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brojZadatka, datumKreiranja, datumPocetkaIzrade, items, statusZadatka);
    }

    @Override
    public String toString() {
        return "Zadatak{" +
                "brojZadatka=" + brojZadatka +
                ", datumKreiranja=" + datumKreiranja +
                ", datumPocetkaIzrade=" + datumPocetkaIzrade +
                ", statusZadatka=" + statusZadatka +
                '}';
    }
}
