package com.sefaunal.resumebuilder.Model;

import com.sefaunal.resumebuilder.Annotation.UniqueEmail;
import com.sefaunal.resumebuilder.Annotation.UniqueUsername;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@Data
@Document
public class User implements UserDetails {
    @Id
    private String ID;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @UniqueUsername
    private String username;

    @NotNull
    @UniqueEmail
    private String email;

    @NotNull
    private String password;

    private String role;

    private String profileImageURI;

    private Instant accountCreationDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}