package by.javaguru.identityservice.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String jwt;
}
