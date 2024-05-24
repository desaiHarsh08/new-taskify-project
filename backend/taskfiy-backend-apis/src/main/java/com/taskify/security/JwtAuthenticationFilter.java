package com.taskify.security;

import com.taskify.app.AppConstants;
import com.taskify.exceptions.GlobalExceptionHandler;
import com.taskify.services.RefreshTokenServices;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private RefreshTokenServices refreshTokenServices;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String email = null, token = null;

        // Get the token from the request's header
        String bearerToken = request.getHeader(AppConstants.JWT_HEADER); // "Bearer <token>"
        String refreshToken = request.getHeader("RefreshToken");

        System.out.println(bearerToken);

        // Check if the token is correct
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
            try {
                email = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException | MalformedJwtException illegalArgumentException) {
                throw new IllegalArgumentException(AppConstants.ARGUMENT_EXCEPTION_MESSAGE);
            } catch (ExpiredJwtException expiredJwtException) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT expired");
                return;
//                throw expiredJwtException;


            }
        }

        // Validate the token
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            if(this.jwtTokenHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            else {
                throw new RuntimeException("INVALID JWT TOKEN");
            }
        }


        //  Forward the request further
        filterChain.doFilter(request, response);

    }
}
