package by.javaguru.emailnotificationservice.database.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "get_product")
public class ProductGetEventEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private BigDecimal price;

    @Column(nullable = false)
    private String productId;

    public ProductGetEventEntity(String title, BigDecimal price, String productId) {
        this.title = title;
        this.price = price;
        this.productId = productId;
    }

    public ProductGetEventEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
