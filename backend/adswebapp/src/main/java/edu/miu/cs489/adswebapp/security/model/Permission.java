package edu.miu.cs489.adswebapp.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    DENTIST_READ("dentist:read"),
    DENTIST_WRITE("dentist:write"),
    PATIENT_READ("patient:read"),
    PATIENT_WRITE("patient:write"),
    OFFICE_ADMIN_READ("office-admin:read"),
    OFFICE_ADMIN_WRITE("office-admin:write"),
    OFFICE_ADMIN_DELETE("office-admin:delete");

    private final String permission;
}
