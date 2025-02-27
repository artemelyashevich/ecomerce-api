package com.elyashevich.auth.service.impl;

import com.elyashevich.auth.api.client.UserRestClient;
import com.elyashevich.auth.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRestClient userRestClient;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        var user = this.userRestClient.findByEmail(email);

        return new User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(Role::name)
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }
}
