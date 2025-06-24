package vn.leoo.auth.util;

import org.springframework.security.core.context.SecurityContextHolder;

import vn.leoo.auth.security.UserPrincipal;

import org.springframework.security.core.Authentication;

public class SecurityUtil {

    public static UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            return (UserPrincipal) authentication.getPrincipal();
        }

        return null;
    }

    public static String getCurrentUserId() {
        UserPrincipal userPrincipal = getCurrentUser();
        return userPrincipal != null ? userPrincipal.getId() : null;
    }

    public static String getCurrentUsername() {
        UserPrincipal userPrincipal = getCurrentUser();
        return userPrincipal != null ? userPrincipal.getUsername() : null;
    }

}
