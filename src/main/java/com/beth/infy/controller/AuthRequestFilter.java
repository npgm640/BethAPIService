package com.beth.infy.controller;

import com.beth.infy.auth.AuthorizationUtil;
import com.beth.infy.service.AuthorizationService;
import com.beth.infy.util.CommonConstants;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AuthorizationService jwtUserService;

    @Autowired
    private AuthorizationUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String requestAuthorizationHeader = httpServletRequest.getHeader(CommonConstants.AUTHORIZATION_HEADER);

        String userName = null;
        String jwtToken = null;

        if (null != requestAuthorizationHeader && requestAuthorizationHeader.startsWith(CommonConstants.AUTHORIZATION_BEARER)) {
            jwtToken = requestAuthorizationHeader.substring(7);
            try {
                userName = jwtUtil.getUserNameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt Token expired");
            }
        } else {
            logger.warn("JWT TOken does not begin with Bearer String ");
        }

        //if you reached here, then validate the token
        if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserService.loadUserByUsername(userName);
            /* if token is valid configure Spring Security to manually set
			 * authentication
             */
             if (jwtUtil.validateToken(jwtToken, userDetails)) {
                 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                         userDetails, null, userDetails.getAuthorities());
                 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                 /*	 After setting the Authentication in the context, we specify
				* that the current user is authenticated. So it passes the
				* Spring Security Configurations successfully.
                  */
                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
             }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
