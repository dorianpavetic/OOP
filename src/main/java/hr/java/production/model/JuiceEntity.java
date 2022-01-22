package hr.java.production.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public abstract class JuiceEntity extends NamedEntity {
    private LocalDateTime createDateTime;
    private Item[] items;

    private BigDecimal weight; //kg

    public JuiceEntity(String name, LocalDateTime createDateTime, Item[] items, BigDecimal weight) {
        super(name);
        this.createDateTime = createDateTime;
        this.items = items;
        this.weight = weight;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return new StringBuilder(JuiceEntity.class.getSimpleName())
                .append(" created at ")
                .append(createDateTime.toString())
                .append(" weights ")
                .append(weight)
                .append(" kg, and has ")
                .append(items.length)
                .append(" items.")
                .toString();
    }
}
