package com.taskify.exceptions;

import io.jsonwebtoken.ExpiredJwtException;

public class ExpiredRefreshTokenException extends RuntimeException {
    public String refreshToken;
    public String username;

    public ExpiredRefreshTokenException(String refreshToken, String username) {
        super(String.format("Session Expired for %s, Please login again!", username));
        this.refreshToken = refreshToken;
        this.username = username;
    }
}
