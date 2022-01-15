package hr.java.production.model;

import java.math.BigDecimal;

public class Item extends Printable implements Displayable {
    private String name;
    private Category category;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal length;
    private BigDecimal productionCost;
    private BigDecimal sellingPrice;

    public Item(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Integer index) {
        super(index);
        this.name = name;
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(BigDecimal productionCost) {
        this.productionCost = productionCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getVolume() {
        return width.multiply(height).multiply(length);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(Item.class.getSimpleName())
                .append(" ")
                .append(name)
                .append(" (")
                .append(category.toString())
                .append(", width=")
                .append(width)
                .append(", height=")
                .append(height)
                .append(", length=")
                .append(length)
                .append(", volume=")
                .append(getVolume())
                .append(", productionCost=")
                .append(productionCost)
                .append(", sellingPrice=")
                .append(sellingPrice)
                .append(")")
                .toString();
    }

    @Override
    public String toShortString() {
        return new StringBuilder(Item.class.getSimpleName())
                .append(" ")
                .append(name)
                .append(" ")
                .append(width)
                .append("x")
                .append(height)
                .append("x")
                .append(length)
                .append("=")
                .append(getVolume())
                .append(" (")
                .append(category.toString())
                .append(". Price: ")
                .append(sellingPrice)
                .append(" (prod. cost: ")
                .append(productionCost)
                .append(")")
                .toString();

    }
}
