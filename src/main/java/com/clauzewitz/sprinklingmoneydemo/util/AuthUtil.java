package com.clauzewitz.sprinklingmoneydemo.util;

import com.clauzewitz.sprinklingmoneydemo.config.auth.AuthToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AuthUtil {

    public static AuthToken getAuthToken() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        return (AuthToken) authenticationToken.getPrincipal();
    }

    public static long getUserId() {
        AuthToken authToken = AuthUtil.getAuthToken();

        if (authToken == null || Long.parseLong(authToken.getUserId()) <= 0L) {
            return -1L;
        }

        return Long.parseLong(authToken.getUserId());
    }

    public static String getRoomId() {
        AuthToken authToken = AuthUtil.getAuthToken();

        if (authToken == null || !StringUtils.hasText(authToken.getRoomId())) {
            return null;
        }

        return authToken.getRoomId();
    }
}
