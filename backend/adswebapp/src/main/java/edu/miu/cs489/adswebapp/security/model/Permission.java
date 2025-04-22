package edu.miu.cs489.adswebapp.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    // Appointment-related permissions
    APPOINTMENT_MAKE("appointment:make"),
    APPOINTMENT_CANCEL("appointment:cancel"),
    APPOINTMENT_SCHEDULE("appointment:schedule"),
    APPOINTMENT_COMPLETE("appointment:complete"),
    APPOINTMENT_READ("appointment:read"),

    // Surgery-related permissions
    SURGERY_WRITE("surgery:write"),
    SURGERY_READ("surgery:read"),

    // Bill-related permissions
    BILL_READ("bill:read"),
    BILL_PAY("bill:pay"),

    // Patient-related permissions
    PATIENT_READ("patient:read"),

    // Dentist-related permissions
    DENTIST_WRITE("dentist:write"),
    DENTIST_READ("dentist:read"),

    // Credential-related permissions
    CREDENTIAL_WRITE("credential:write");

    private final String permission;
}
