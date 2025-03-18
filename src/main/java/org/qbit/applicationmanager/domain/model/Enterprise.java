package org.qbit.applicationmanager.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "enterprise")
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enterpriseId;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Enterprise() {}

    public Enterprise(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Long getEnterpriseId() { return enterpriseId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
