package at.htl.leonding.login.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;


    public UserSession(User userId) {
        this.userId = userId;
    }

    public UserSession() {

    }

    public UUID getToken() {
        return token;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
