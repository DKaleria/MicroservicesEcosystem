package by.javaguru.core.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;

    public UserDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserDto() {
    }
}