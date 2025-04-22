package edu.miu.cs489.adswebapp.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum Role {
    PATIENT(Set.of(Permission.PATIENT_WRITE, Permission.PATIENT_READ)),
    OFFICE_ADMIN(Set.of(Permission.OFFICE_ADMIN_WRITE, Permission.OFFICE_ADMIN_READ)),
    DENTIST(Set.of(Permission.DENTIST_WRITE, Permission.DENTIST_READ));

    private final Set<Permission> permissions;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
