package com.clauzewitz.sprinklingmoneydemo.config.filter;

import com.clauzewitz.sprinklingmoneydemo.config.auth.AuthToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (this.isContainAuthHeader(request)) {
            String userId = request.getHeader("X-USER-ID");
            String roomId = request.getHeader("X-ROOM-ID");

            UserDetails userDetails = AuthToken.builder()
                    .userId(userId)
                    .roomId(roomId)
                    .build();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isContainAuthHeader(HttpServletRequest request) {
        String userToken = request.getHeader("X-USER-ID");
        return StringUtils.hasText(userToken) && userToken.matches("\\d+");
    }
}
