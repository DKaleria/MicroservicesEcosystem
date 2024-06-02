package by.javaguru.prroductservice.dto;

import by.javaguru.core.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductDto {
    private String id;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    private UserDto user;
}
