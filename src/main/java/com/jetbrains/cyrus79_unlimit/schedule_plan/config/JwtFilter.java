package com.jetbrains.cyrus79_unlimit.schedule_plan.config;

import com.jetbrains.cyrus79_unlimit.schedule_plan.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

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
//            try {
//                Claims claims = jwtUntil.validateToken(token);
//                String username = claims.getSubject();
//
//                if(username != null) {
//                    UsernamePasswordAuthenticationToken auth =
//                            new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
//                    SecurityContextHolder.getContext().setAuthentication(auth);
//                }
//            } catch (Exception e) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
//                return;
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("Username from Token: " + username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("UserDetail found : " + userDetails.getUsername());

                if (jwtUntil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authentication set: " + SecurityContextHolder.getContext().getAuthentication());
                } else {
                    System.out.println("Token validation failed");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
