package com.expen.expensemanagerapi.filters;

import com.expen.expensemanagerapi.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // typecast to hhtp request and response
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        //

        /*
        * to access any protected resource, Client should pass the token in Request as authorization header
        * we want the String to be a Bearer String:
        * Authorization Bearer <Token>
        * */
        String authHeader = httpRequest.getHeader("Authorization");
        if(authHeader != null){
            String [] authHeaderArr = authHeader.split("Bearer ");
            if(authHeaderArr.length > 1 && authHeaderArr[1] != null){
                String token = authHeaderArr[1];
                try {
                    // fetch the claims to get all user details
                    /*
                    * on parser -> provide API Secret Key
                    * parse claim !
                    * and fetch body !
                    * */
                    Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY)
                            .parseClaimsJws(token).getBody();

                    // modify request object, add attribute of user_id
                    httpRequest.setAttribute("userId", Integer.parseInt(claims.get("userId").toString()));

                    /*
                    * Once the request passes through this filter we can access current user_id
                    * */
                } catch (Exception e){
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
                    return;
                }
            } else{
                // AuthHeader Present, but not sent in correct format
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization Token must be Bearer [token]");
                return;
            }
        } else{
            // AuthHeader is missing
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization Token must be provided");
            return;
        }
        // Validated !
        // pass req and response obj
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
