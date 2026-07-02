package com.financialledgersystem.config;


import jakarta.servlet.*;
import jakarta.servlet.http.*;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.
        SecurityContextHolder;

import org.springframework.security.core.userdetails.
        UserDetails;

import org.springframework.security.web.authentication.
        WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.
        OncePerRequestFilter;

import com.financialledgersystem.service.CustomUserDetailsService;
import com.financialledgersystem.service.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

	@Autowired
    private  JwtService jwtService;

	@Autowired
    private  CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain)
	        throws ServletException, IOException {

	    String authHeader = request.getHeader("Authorization");

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    try {

	        String token = authHeader.substring(7);

	        String username = jwtService.extractUsername(token);

	        if (username != null &&
	                SecurityContextHolder.getContext().getAuthentication() == null) {

	            UserDetails userDetails =
	                    userDetailsService.loadUserByUsername(username);

	            if (jwtService.validateToken(token,userDetails)) {

	                UsernamePasswordAuthenticationToken authToken =
	                        new UsernamePasswordAuthenticationToken(
	                                userDetails,
	                                null,
	                                userDetails.getAuthorities());

	                authToken.setDetails(
	                        new WebAuthenticationDetailsSource()
	                                .buildDetails(request));

	                SecurityContextHolder.getContext()
	                        .setAuthentication(authToken);
	            }
	        }

	    } catch (Exception e) {

	        // Invalid token → continue without authentication
	        System.out.println("Invalid JWT : " + e.getMessage());

	    }

	    filterChain.doFilter(request, response);
	}
}
