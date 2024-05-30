package by.javaguru.userservice.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserGetEvent {
    private Long id;
    private String name;
}
