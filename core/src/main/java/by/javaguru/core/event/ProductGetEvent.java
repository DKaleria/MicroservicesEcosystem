package by.javaguru.core.event;

import by.javaguru.core.dto.UserDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductGetEvent {
    private String id;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    private UserDto user;

    public ProductGetEvent(String id, String title, BigDecimal price, Integer quantity, UserDto user) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.user = user;
    }

    public ProductGetEvent() {
    }
}