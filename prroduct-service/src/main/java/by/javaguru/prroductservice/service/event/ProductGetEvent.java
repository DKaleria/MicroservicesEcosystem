package by.javaguru.prroductservice.service.event;

import by.javaguru.userservice.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ProductGetEvent {
    private String id;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    private UserDto user;
}