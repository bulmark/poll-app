package com.example.pollprojectmain.util;

import com.example.pollprojectmain.service.Impl.UserDetailsImpl;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    @Nullable
    public static UserDetailsImpl getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            return (UserDetailsImpl) authentication.getPrincipal();
        }
        return null;
    }
}
