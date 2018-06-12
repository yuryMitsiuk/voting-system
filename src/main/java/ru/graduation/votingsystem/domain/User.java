package ru.graduation.votingsystem.domain;

import org.springframework.util.CollectionUtils;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractNamedEntity {

    @Email
    @Column(name = "email", nullable = false, unique = true)
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    @Column(name = "votedtoday", nullable = false, columnDefinition = "bool default false")
    private boolean votedToday = false;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isVotedToday(), user.getRoles());
    }

    public User(Long id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, false, EnumSet.of(role, roles));
    }

    public User(Long id, String name, String email, String password, boolean votedToday, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.votedToday = votedToday;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVotedToday() {
        return votedToday;
    }

    public void setVotedToday(boolean votedToday) {
        this.votedToday = votedToday;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

    @Override
    public String toString() {
        return "User (" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", votedToday=" + votedToday +
                ", roles=" + roles +
                ')';
    }
}
