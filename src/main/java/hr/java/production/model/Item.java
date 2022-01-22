package hr.java.production.model;

import java.math.BigDecimal;

/**
 * Item of certain category, can be sold or produced.
 */
public class Item extends NamedEntity {
    private Category category;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal length;
    private BigDecimal productionCost;
    private BigDecimal sellingPrice;
    private Discount discount;

    /**
     * @param name name of the item.
     * @param category selected category of the item.
     * @param width width of item in inches.
     * @param height height of item in inches.
     * @param length length of item in inches.
     * @param productionCost amount item costs to make.
     * @param sellingPrice amount item is selling for.
     * @param discount discount data about the item.
     */
    public Item(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length,
                BigDecimal productionCost, BigDecimal sellingPrice, Discount discount) {
        super(name);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
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

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return new StringBuilder(Item.class.getSimpleName())
                .append(" ")
                .append(getName())
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
