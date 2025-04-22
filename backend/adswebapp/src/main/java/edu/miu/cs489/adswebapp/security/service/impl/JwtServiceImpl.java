package edu.miu.cs489.adswebapp.security.service.impl;

import edu.miu.cs489.adswebapp.security.model.User;
import edu.miu.cs489.adswebapp.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@Component
public class JwtServiceImpl implements JwtService {
    private final String JWT_SECRET_KEY;

    public JwtServiceImpl(@Value("${jwt.secretKey}") String jwtSecretKey) {
        this.JWT_SECRET_KEY = jwtSecretKey;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        User user = (User) userDetails;

        return Jwts.builder()
                   .signWith(signInKey())
                   .issuer("suman-adhikari.com.np")
                   .issuedAt(new Date())
                   .expiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                   .subject(userDetails.getUsername())
                   .claim("user_id", user.getId())
                   .claim("authorities", populateAuthorities(user.getAuthorities()))
                   .compact();
    }

    @Override
    public String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
    }

    @Override
    public Claims parseSignedClaims(String token) {
        return Jwts.parser().verifyWith(signInKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey signInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET_KEY));
    }
}
