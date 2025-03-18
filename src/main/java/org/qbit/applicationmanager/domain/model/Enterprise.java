package org.qbit.applicationmanager.domain.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "enterprise",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "name"})
        }
)
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enterpriseId;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Enterprise that = (Enterprise) o;
        return Objects.equals(enterpriseId, that.enterpriseId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(enterpriseId);
    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "enterpriseId=" + enterpriseId +
                ", name='" + name + '\'' +
                ", user=" + user +
                '}';
    }
}
