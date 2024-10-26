package by.javaguru.identityservice.usecaseses.events;

import lombok.*;
import java.time.LocalDate;

@ToString
public class AuthUserGotEvent {
    String id;

    private String username;

    private LocalDate birthDate;

    private String firstname;

    private String lastname;

    private String password;

    public AuthUserGotEvent(String id, String username, LocalDate birthDate, String firstname, String lastname, String password) {
        this.id = id;
        this.username = username;
        this.birthDate = birthDate;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    public AuthUserGotEvent() {
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
