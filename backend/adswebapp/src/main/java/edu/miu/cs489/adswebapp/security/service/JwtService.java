package edu.miu.cs489.adswebapp.security.service;

import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;


public interface JwtService {
    String generateToken(UserDetails userDetails);
    String populateAuthorities(Collection<? extends GrantedAuthority> authorities);
    Claims parseSignedClaims(String token);
}
