package by.javaguru.prroductservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Product {
    private String id;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    private Long userId;
}
