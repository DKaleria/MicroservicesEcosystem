package by.javaguru.prroductservice.dto;

import by.javaguru.userservice.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private UserDto user;
}
