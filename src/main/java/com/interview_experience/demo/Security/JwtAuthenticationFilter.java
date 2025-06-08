package com.interview_experience.demo.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.ExpiredJwtException;
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

    private static final Logger logger=  LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader=request.getHeader("Authorizationi");
        logger.info("Header : {}");
        String username=null;
        String token=null;

        System.out.println(requestHeader);

        if (requestHeader !=null && requestHeader.startsWith("Bearer")){
            token =requestHeader.substring(7);

            try {
                username=this.jwtHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException e){
                logger.info("illegal argument while fetching  the username !!");
                e.printStackTrace();
            }
            catch (ExpiredJwtException e){
                logger.info("given jwt token is expired !!");
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken=this.jwtHelper.validateToken(token,userDetails);

            if(validateToken){

                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else{
                logger.info("Validation failed");
            }
        }

        filterChain.doFilter(request,response);


    }
}
