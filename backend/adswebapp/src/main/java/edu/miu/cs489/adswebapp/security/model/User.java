package edu.miu.cs489.adswebapp.security.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private                  Integer id;
    @Column(name = "first_name") private                                             String  firstName;
    @Column(name = "last_name") private                                              String  lastName;
    @Column(name = "username", unique = true, nullable = false, length = 50) private String  username;
    @Column(name = "password_hash") private                                          String  password;
    @Column(name = "phone_number", nullable = false, length = 20) private            String  phoneNumber;
    @Column(name = "email", unique = true, nullable = false, length = 100) private   String  email;
    @Enumerated(EnumType.STRING) @Column(name = "role", nullable = false) private    Role    role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }
}
