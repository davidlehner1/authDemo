package at.htl.leonding.login.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class UserSession {
    @Id
    private UUID token;
    private String name;

    public UserSession(UUID token, String name) {
        this.token = token;
        this.name = name;
    }

    public UserSession() {

    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
