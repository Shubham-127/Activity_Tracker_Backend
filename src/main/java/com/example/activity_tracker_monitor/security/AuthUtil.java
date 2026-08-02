package com.example.activity_tracker_monitor.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public class AuthUtil {
    public static Long currentDeviceId(Authentication auth) {
        return Long.parseLong((String) auth.getPrincipal());
    }
}
