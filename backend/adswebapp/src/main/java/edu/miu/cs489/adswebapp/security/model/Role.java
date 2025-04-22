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
    PATIENT(Set.of(
            Permission.APPOINTMENT_MAKE, Permission.APPOINTMENT_CANCEL, Permission.APPOINTMENT_READ,
            Permission.BILL_READ, Permission.BILL_PAY, Permission.SURGERY_READ, Permission.PATIENT_READ,
            Permission.CREDENTIAL_WRITE
                  )),
    DENTIST(Set.of(
            Permission.DENTIST_READ, Permission.APPOINTMENT_READ, Permission.APPOINTMENT_COMPLETE,
            Permission.SURGERY_READ, Permission.CREDENTIAL_WRITE
                  )),
    OFFICE_ADMIN(Set.of(
            Permission.SURGERY_READ, Permission.PATIENT_READ, Permission.DENTIST_READ, Permission.DENTIST_WRITE,
            Permission.APPOINTMENT_SCHEDULE, Permission.APPOINTMENT_CANCEL, Permission.CREDENTIAL_WRITE
                       )),
    ;

    private final Set<Permission> permissions;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions().stream()
                                                                   .map(permission -> new SimpleGrantedAuthority(
                                                                           permission.getPermission()))
                                                                   .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
