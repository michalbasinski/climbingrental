package pl.onsight.wypozyczalnia.model.entity;

import javax.persistence.*;


@Entity(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productName;
    private Double price;
    @Column(length = 10000)
    private String description;
    private String smallImage;
    private String bigImage;
    private String tags;
    private Integer quantity;
    private Double deposit;

    public ProductEntity() {
    }

    public ProductEntity(String productName, Double price, String description, String smallImage, String bigImage, String tags, Integer quantity, Double deposit) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.smallImage = smallImage;
        this.bigImage = bigImage;
        this.tags = tags;
        this.quantity = quantity;
        this.deposit = deposit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity product = (ProductEntity) o;

        if (productName != null ? !productName.equals(product.productName) : product.productName != null) return false;
        if (price != null ? !price.equals(product.price) : product.price != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        if (smallImage != null ? !smallImage.equals(product.smallImage) : product.smallImage != null) return false;
        if (bigImage != null ? !bigImage.equals(product.bigImage) : product.bigImage != null) return false;
        return tags != null ? tags.equals(product.tags) : product.tags == null;

    }

    @Override
    public int hashCode() {
        int result = productName != null ? productName.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (smallImage != null ? smallImage.hashCode() : 0);
        result = 31 * result + (bigImage != null ? bigImage.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}