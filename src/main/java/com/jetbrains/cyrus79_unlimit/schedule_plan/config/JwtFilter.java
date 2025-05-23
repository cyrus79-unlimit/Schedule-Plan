package com.jetbrains.cyrus79_unlimit.schedule_plan.config;

import com.jetbrains.cyrus79_unlimit.schedule_plan.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUntil jwtUntil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String autHeader = request.getHeader("Authorization");

        if(autHeader != null && autHeader.startsWith("Bearer ")) {
            String token = autHeader.substring(7);
            String username = jwtUntil.getUsernameFromToken(token);
//
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("Username from Token: " + username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("UserDetail found : " + userDetails.getUsername());


                if (jwtUntil.validateToken(token, userDetails)) {
                    // Get role from token
                    String role = jwtUntil.getRoleFromToken(token);
                    System.out.println("Extracted Role: " + role);

                    // Add role prefix: Spring Security expects "ROLE_"
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                    // Build auth token with authorities
//                    UsernamePasswordAuthenticationToken authToken =
//                            new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null, List.of(authority));

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authentication set: " + authority.getAuthority());
                } else {
                    System.out.println("Token validation failed");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
