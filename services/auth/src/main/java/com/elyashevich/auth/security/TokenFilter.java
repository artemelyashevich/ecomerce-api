package com.elyashevich.auth.security;

import com.elyashevich.auth.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.elyashevich.auth.util.TokenConstantUtil.*;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private static final int BEGIN_INDEX = 7;

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        var header = request.getHeader(HEADER_STRING);
        String jwt = null;
        String email = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            jwt = header.substring(BEGIN_INDEX);
            email = TokenUtil.extractEmailClaims(jwt);
        }
        if (email != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            var token = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    TokenUtil.getRoles(jwt)
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }
}
